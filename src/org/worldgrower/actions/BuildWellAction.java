/*******************************************************************************
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.worldgrower.actions;

import java.io.ObjectStreamException;

import org.worldgrower.Constants;
import org.worldgrower.Reach;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.attribute.SkillUtils;
import org.worldgrower.generator.BuildingDimensions;
import org.worldgrower.generator.BuildingGenerator;
import org.worldgrower.gui.ImageIds;
import org.worldgrower.gui.music.SoundIds;

public class BuildWellAction implements BuildAction {

	private static final int REQUIRED_WOOD = 6;
	
	@Override
	public void execute(WorldObject performer, WorldObject target, int[] args, World world) {
		int x = (Integer)target.getProperty(Constants.X);
		int y = (Integer)target.getProperty(Constants.Y);
		
		BuildingGenerator.buildWell(x, y, world, SkillUtils.useSkill(performer, Constants.CARPENTRY_SKILL, world.getWorldStateChangedListeners()));
		
		performer.getProperty(Constants.INVENTORY).removeQuantity(Constants.WOOD, REQUIRED_WOOD);
	}

	@Override
	public boolean isValidTarget(WorldObject performer, WorldObject target, World world) {
		return CraftUtils.isValidBuildTarget(this, performer, target, world);
	}
	
	@Override
	public boolean isActionPossible(WorldObject performer, WorldObject target, int[] args, World world) {
		return CraftUtils.hasEnoughResources(performer, Constants.WOOD, REQUIRED_WOOD);
	}

	@Override
	public int distance(WorldObject performer, WorldObject target, int[] args, World world) {
		return Reach.evaluateTarget(performer, args, target, 1);
	}
	
	@Override
	public String getRequirementsDescription() {
		return CraftUtils.getRequirementsDescription(Constants.WOOD, REQUIRED_WOOD);
	}
	
	@Override
	public String getDescription() {
		return "a well provides water";
	}

	@Override
	public boolean requiresArguments() {
		return true;
	}
	
	@Override
	public String getDescription(WorldObject performer, WorldObject target, int[] args, World world) {
		return "building a well";
	}

	@Override
	public String getSimpleDescription() {
		return "build well";
	}
	
	public Object readResolve() throws ObjectStreamException {
		return readResolveImpl();
	}

	@Override
	public int getWidth() {
		return BuildingDimensions.WELL.getPlacementWidth();
	}

	@Override
	public int getHeight() {
		return BuildingDimensions.WELL.getPlacementHeight();
	}
	
	@Override
	public ImageIds getImageIds(WorldObject performer) {
		return ImageIds.EMPTY_WELL;
	}

	@Override
	public SoundIds getSoundId() {
		return SoundIds.BUILD_WOODEN_BUILDING;
	}
	
	public static boolean hasEnoughWood(WorldObject performer) {
		return performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.WOOD) >= REQUIRED_WOOD;
	}
}
