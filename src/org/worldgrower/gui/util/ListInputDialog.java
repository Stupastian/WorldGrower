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
package org.worldgrower.gui.util;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.worldgrower.gui.AbstractDialog;
import org.worldgrower.gui.ImageInfoReader;
import org.worldgrower.gui.music.SoundIdReader;

public class ListInputDialog extends AbstractDialog {

	private String value = null;
	private JComboBox<String> comboBox;
	
	public ListInputDialog(String question, ListData listData, ImageInfoReader imageInfoReader, SoundIdReader soundIdReader, JFrame parentFrame) {
		this(question, null, listData, imageInfoReader, soundIdReader, parentFrame);
	}
	
	//TODO: use icon
	public ListInputDialog(String question, Icon icon, ListData listData, ImageInfoReader imageInfoReader, SoundIdReader soundIdReader, JFrame parentFrame) {
		super(600, 210, imageInfoReader);
		
		JLabel label = JLabelFactory.createJLabel(question);
		label.setBounds(16, 16, 565, 50);
		addComponent(label);
		
		comboBox = JComboBoxFactory.createJComboBox(listData, imageInfoReader);
		comboBox.setBounds(16, 70, 565, 50);
		addComponent(comboBox);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPane.setOpaque(false);
		buttonPane.setBounds(16, 153, 575, 50);
		addComponent(buttonPane);

		JButton cancelButton = JButtonFactory.createButton("Cancel", imageInfoReader, soundIdReader);
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
		JButton okButton = JButtonFactory.createButton(" OK ", imageInfoReader, soundIdReader);
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		addActions(okButton, cancelButton);
		DialogUtils.createDialogBackPanel(this, parentFrame.getContentPane());
	}
	
	public String showMe() {
		setVisible(true);
		return value;
	}

	private void addActions(JButton okButton, JButton cancelButton) {
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				value = (String) comboBox.getSelectedItem();
				ListInputDialog.this.dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				value = null;
				ListInputDialog.this.dispose();
			}
		});
	}
}
