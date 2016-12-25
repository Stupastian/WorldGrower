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
import java.util.List;

import org.worldgrower.Constants;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.goal.FacadeUtils;
import org.worldgrower.gui.ImageIds;

public class DisguiseAction implements DisguiseTargetFactory {

	@Override
	public void execute(WorldObject performer, WorldObject target, int[] args, World world) {
		FacadeUtils.disguise(performer, args[0], world);
	}

	@Override
	public boolean isActionPossible(WorldObject performer, WorldObject target, int[] args, World world) {
		return true;
	}
	
	@Override
	public int distance(WorldObject performer, WorldObject target, int[] args, World world) {
		return 0;
	}
	
	@Override
	public String getRequirementsDescription() {
		return "";
	}
	
	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public boolean requiresArguments() {
		return true;
	}

	@Override
	public boolean isValidTarget(WorldObject performer, WorldObject target, World world) {
		return CraftUtils.isValidTarget(performer, target, world);
	}

	@Override
	public String getDescription(WorldObject performer, WorldObject target, int[] args, World world) {
		return "disguising myself";
	}

	@Override
	public String getSimpleDescription() {
		return "disguise";
	}
	
	public Object readResolve() throws ObjectStreamException {
		return readResolveImpl();
	}

	@Override
	public List<WorldObject> getDisguiseTargets(WorldObject performer, World world) {
		int performerId = performer.getProperty(Constants.ID);
		List<WorldObject> disguiseWorldObjects = world.findWorldObjects(w -> w.getProperty(Constants.WIDTH) == 1 && w.getProperty(Constants.HEIGHT) == 1 && (w.getProperty(Constants.ID) != performerId));
		return disguiseWorldObjects;
	}
	
	@Override
	public ImageIds getImageIds(WorldObject performer) {
		return ImageIds.DISGUISE_MAGIC_SPELL;
	}
}