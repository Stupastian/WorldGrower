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

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JList;
import javax.swing.ListModel;

public class TiledImageJList<E> extends JList<E> {

	private final BufferedImage tileImage;  

    private BufferedImage getTiledImage(ImageInfoReader imageInfoReader) {
    	return (BufferedImage) imageInfoReader.getImage(ImageIds.SCREEN_BACKGROUND, null);
    }  
	
	public TiledImageJList(ImageInfoReader imageInfoReader) {
		super();
		tileImage = getTiledImage(imageInfoReader);
		setOpaque(false);
	}

	public TiledImageJList(E[] listData, ImageInfoReader imageInfoReader) {
		super(listData);
		tileImage = getTiledImage(imageInfoReader);
		setOpaque(false);
	}

	public TiledImageJList(ListModel<E> dataModel, ImageInfoReader imageInfoReader) {
		super(dataModel);
		tileImage = getTiledImage(imageInfoReader);
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		TiledImagePainter.paintComponent(this, g, tileImage);
		super.paintComponent(g);
	}
}