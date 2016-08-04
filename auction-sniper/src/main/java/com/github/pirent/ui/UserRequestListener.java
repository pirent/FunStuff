package com.github.pirent.ui;

import java.awt.event.ActionListener;
import java.util.EventListener;

import com.github.pirent.Item;

/**
 * <p>In order to convert {@link ActionListener} event, which is internal
 * to the user interface framework, to this event, which is about users
 * interacting with an auction.</p>
 * 
 * <p>These are two separate domains and {@link MainWindow}'s job is to
 * translate from one to the other. {@link MainWindow} is not concerned
 * with how any implementation of {@link UserRequestListener} might work
 * - that would to be too much responsibility
 * 
 * @author pirent
 *
 */
public interface UserRequestListener extends EventListener {

	void joinAuction(Item item);
	
}
