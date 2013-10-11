package de.ntcomputer.minecraft.controllablemobs.implementation.nativeinterfaces.classes;

import net.minecraft.server.v1_6_R3.Entity;
import de.ntcomputer.minecraft.controllablemobs.implementation.nativeinterfaces.system.NativeMethodPublic;

public class NotchInterfaceEntity {
	public final GetEntityDistanceSquared METHOD_GETDISTANCETOENTITYSQUARED = new GetEntityDistanceSquared();
	public final GetLocationDistance METHOD_GETDISTANCETOLOCATION = new GetLocationDistance();
	
	public class GetEntityDistanceSquared extends NativeMethodPublic {
		public double invoke(final Entity entity, final Entity target) {
			try {
				// method returns difLocX^2 + difLocY^2 + difLocZ^2 for entity
				return entity.e(target);
			} catch(Throwable e) {
				this.handleException(e);
				return 0;
			}
		}
	}
	
	public class GetLocationDistance extends NativeMethodPublic {
		public double invoke(final Entity entity, final double x, final double y, final double z) {
			try {
				// method returns Math.sqrt( difLocX^2 + difLocY^2 + difLocZ^2 ) for double values
				return entity.f(x, y, z);
			} catch(Throwable e) {
				this.handleException(e);
				return 0;
			}
		}
	}

}
