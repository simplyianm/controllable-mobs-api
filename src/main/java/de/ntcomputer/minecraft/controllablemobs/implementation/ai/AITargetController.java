package de.ntcomputer.minecraft.controllablemobs.implementation.ai;

import org.bukkit.entity.LivingEntity;

import de.ntcomputer.minecraft.controllablemobs.implementation.CraftControllableMob;
import de.ntcomputer.minecraft.controllablemobs.implementation.ai.behaviors.PathfinderGoalActionTarget;
import de.ntcomputer.minecraft.controllablemobs.implementation.nativeinterfaces.NativeInterfaces;

class AITargetController<E extends LivingEntity> extends AIController<E> {

	public AITargetController(CraftControllableMob<E> mob) {
		super(mob, NativeInterfaces.ENTITYINSENTIENT.FIELD_TARGETSELECTOR);
	}

	@Override
	protected void createActionGoals() {
		this.addActionGoal(-1, new PathfinderGoalActionTarget(mob));
	}

}
