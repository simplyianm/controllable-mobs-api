package de.ntcomputer.minecraft.controllablemobs.implementation.ai.behaviors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_6_R2.DistanceComparator;
import net.minecraft.server.v1_6_R2.EntityHuman;
import net.minecraft.server.v1_6_R2.EntityLiving;

import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import de.ntcomputer.minecraft.controllablemobs.implementation.CraftControllableMob;
import de.ntcomputer.minecraft.controllablemobs.implementation.ai.EntitySelector;
import de.ntcomputer.minecraft.controllablemobs.implementation.nativeinterfaces.NmsInterfaces;

public class PathfinderGoalTargetNearest extends PathfinderGoalTargetEx {
	private final float searchDistance;
	private final DistanceComparator comparator;
	private final EntitySelector entitySelector;

	public PathfinderGoalTargetNearest(final CraftControllableMob<?> mob,
			final int maximumNoEyeContactTicks, final boolean ignoreInvulnerability,
			final float maximumDistance, final Class<? extends EntityLiving>[] targetClasses,
			final EntitySelector entitySelector) {
		super(mob, maximumNoEyeContactTicks, ignoreInvulnerability, maximumDistance,	targetClasses);
		this.searchDistance = maximumDistance<=0 ? 500 : maximumDistance;
		this.comparator = new DistanceComparator(entity);
		this.entitySelector = entitySelector;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected boolean canStart() {
		final List<EntityLiving> entities = new ArrayList<EntityLiving>();
		
		for(Class<? extends EntityLiving> targetClass: this.targetClasses) {
			if(targetClass==EntityHuman.class) {
				this.findNearbyPlayersOptimized(this.entity, entities);
			} else {
				entities.addAll(NmsInterfaces.WORLD.METHOD_SEARCHENTITIES.invoke(this.entity.world, targetClass, this.entity.boundingBox.grow(this.searchDistance, this.searchDistance/4.0, this.searchDistance), this.entitySelector));
			}
		}
		
		if(entities.size()>1) Collections.sort(entities, this.comparator);
		
		for(EntityLiving possibleTarget: entities) {
			if(this.target(possibleTarget, TargetReason.CLOSEST_PLAYER)) return true;
		}
		
		return false;
	}
	
	private void findNearbyPlayersOptimized(final EntityLiving entity, final List<EntityLiving> targets) {
		for(int i=0; i<entity.world.players.size(); i++) {
			EntityHuman human = (EntityHuman) entity.world.players.get(i);
			if(human!=null && !human.dead) {
				if(this.entitySelector==null || this.entitySelector.isEntityValid(human)) {
					targets.add(human);
				}
			}
		}
	}

}