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
package org.worldgrower.gui;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import org.worldgrower.DungeonMaster;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.actions.OrganizationNamer;
import org.worldgrower.deity.Deity;
import org.worldgrower.gui.music.SoundIdReader;
import org.worldgrower.gui.start.Game;
import org.worldgrower.gui.util.ListInputDialog;
import org.worldgrower.profession.Profession;
import org.worldgrower.profession.Professions;

public class GuiCreateOrganizationAction extends AbstractAction {

	private WorldObject playerCharacter;
	private ImageInfoReader imageInfoReader;
	private SoundIdReader soundIdReader;
	private World world;
	private WorldPanel parent;
	private DungeonMaster dungeonMaster;
	private JFrame parentFrame;
	
	public GuiCreateOrganizationAction(WorldObject playerCharacter, ImageInfoReader imageInfoReader, SoundIdReader soundIdReader, World world, WorldPanel parent, DungeonMaster dungeonMaster, JFrame parentFrame) {
		super();
		this.playerCharacter = playerCharacter;
		this.imageInfoReader = imageInfoReader;
		this.soundIdReader = soundIdReader;
		this.world = world;
		this.parent = parent;
		this.dungeonMaster = dungeonMaster;
		this.parentFrame = parentFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] organizationTypes = { "profession", "religion" };
		String organizationType = new ListInputDialog("Choose Organization Type", organizationTypes, soundIdReader, parentFrame).showMe();
		if (organizationType != null) {
			if (organizationType.equals(organizationTypes[0])) {
				createProfessionOrganization();
			} else if (organizationType.equals(organizationTypes[1])) {
				createReligionOrganization();
			}
		}
	}

	private void createProfessionOrganization() {
		String[] professionNames = Professions.getDescriptions().toArray(new String[0]);
		String professionName = new ListInputDialog("Choose Profession", professionNames, soundIdReader, parentFrame).showMe();
		if (professionName != null) {
			Profession profession = Professions.getProfessionByDescription(professionName);
			int professionIndex = Professions.indexOf(profession);
			
			String[] organizationNames = new OrganizationNamer().getProfessionOrganizationNames(profession, world).toArray(new String[0]);
			String organizationName = new ListInputDialog("Choose Organization name", organizationNames, soundIdReader, parentFrame).showMe();
			
			if (organizationName != null) {
				int indexOfOrganization = Arrays.asList(organizationNames).indexOf(organizationName);
				
				Game.executeActionAndMoveIntelligentWorldObjects(playerCharacter, Actions.CREATE_PROFESSION_ORGANIZATION_ACTION, new int[] { professionIndex, indexOfOrganization}, world, dungeonMaster, playerCharacter, parent, soundIdReader);
			}
		}
	}
	
	private void createReligionOrganization() {
		String[] deityNames = Deity.getAllDeityNames().toArray(new String[0]);
		String deityName = new ListInputDialog("Choose Deity", deityNames, soundIdReader, parentFrame).showMe();
		if (deityName != null) {
			Deity deity = Deity.getDeityByDescription(deityName);
			int deityIndex = Deity.ALL_DEITIES.indexOf(deity);
			
			String[] organizationNames = new OrganizationNamer().getDeityOrganizationNames(deity, world).toArray(new String[0]);
			String organizationName = new ListInputDialog("Choose Organization name", organizationNames, soundIdReader, parentFrame).showMe();
			
			if (organizationName != null) {
				int indexOfOrganization = Arrays.asList(organizationNames).indexOf(organizationName);
				
				List<String> possibleGoalsList = deity.getOrganizationGoalDescriptions();
				possibleGoalsList.add(0, "No goal");
				String[] possibleGoals = possibleGoalsList.toArray(new String[0]);
				String possibleGoal = new ListInputDialog("Choose Goal", possibleGoals, soundIdReader, parentFrame).showMe();
				if (possibleGoal != null) {
					int indexOfGoal = deity.getOrganizationGoalDescriptions().indexOf(possibleGoal);
					Game.executeActionAndMoveIntelligentWorldObjects(playerCharacter, Actions.CREATE_RELIGION_ORGANIZATION_ACTION, new int[] { deityIndex, indexOfOrganization, indexOfGoal}, world, dungeonMaster, playerCharacter, parent, soundIdReader);
				}
			}
		}
	}
}