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
package org.worldgrower.profession;

import java.io.ObjectStreamException;
import java.util.Arrays;
import java.util.List;

import org.worldgrower.Constants;
import org.worldgrower.attribute.SkillProperty;
import org.worldgrower.generator.Item;
import org.worldgrower.goal.Goal;
import org.worldgrower.goal.Goals;
import org.worldgrower.gui.ImageIds;

public class GraveDiggerProfession implements Profession {

	public GraveDiggerProfession(List<Profession> allProfessions) {
		allProfessions.add(this);
	}

	@Override
	public String getDescription() {
		return "grave digger";
	}

	@Override
	public List<Goal> getProfessionGoals() {
		return Arrays.asList(
				Goals.GATHER_REMAINS_GOAL, 
				Goals.CREATE_GRAVE_GOAL,
				Goals.SELL_UNUSED_ITEMS_GOAL);
	}

	@Override
	public SkillProperty getSkillProperty() {
		return Constants.ALCHEMY_SKILL;
	}

	public Object readResolve() throws ObjectStreamException {
		return readResolveImpl();
	}
	
	@Override
	public boolean isPaidByVillagerLeader() {
		return false;
	}
	
	@Override
	public boolean avoidEnemies() {
		return true;
	}
	
	//TODO: sell services
	@Override
	public List<Item> getSellItems() {
		return Arrays.asList();
	}

	@Override
	public ImageIds getImageId() {
		return ImageIds.GRAVE;
	}
}
