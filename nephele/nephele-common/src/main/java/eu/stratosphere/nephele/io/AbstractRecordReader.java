/***********************************************************************************************************************
 *
 * Copyright (C) 2010 by the Stratosphere project (http://stratosphere.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 **********************************************************************************************************************/

package eu.stratosphere.nephele.io;

import java.io.IOException;

import eu.stratosphere.nephele.event.task.AbstractTaskEvent;
import eu.stratosphere.nephele.event.task.EventListener;
import eu.stratosphere.nephele.event.task.EventNotificationManager;

/**
 * This is an abstract base class for a record reader, either dealing with mutable or immutable records,
 * and dealing with reads from single gates (single end points) or multiple gates (union).
 */
public abstract class AbstractRecordReader {
	
	
	private final EventNotificationManager eventHandler = new EventNotificationManager();
	
	// --------------------------------------------------------------------------------------------

	/**
	 * Subscribes the listener object to receive events of the given type.
	 * 
	 * @param eventListener
	 *        the listener object to register
	 * @param eventType
	 *        the type of event to register the listener for
	 */
	public void subscribeToEvent(EventListener eventListener, Class<? extends AbstractTaskEvent> eventType) {
		this.eventHandler.subscribeToEvent(eventListener, eventType);
	}

	/**
	 * Removes the subscription for events of the given type for the listener object.
	 * 
	 * @param eventListener The listener object to cancel the subscription for.
	 * @param eventType The type of the event to cancel the subscription for.
	 */
	public void unsubscribeFromEvent(EventListener eventListener, Class<? extends AbstractTaskEvent> eventType) {
		this.eventHandler.unsubscribeFromEvent(eventListener, eventType);
	}

	/**
	 * Publishes an event.
	 * 
	 * @param event The event to be published.
	 * @throws IOException Thrown, if an error occurs while transmitting the event.
	 * @throws InterruptedException Thrown, if the thread is interrupted while waiting for the event to be published.
	 */
	public abstract void publishEvent(AbstractTaskEvent event) throws IOException, InterruptedException;
	
	
	protected void handleEvent(AbstractTaskEvent evt) {
		this.eventHandler.deliverEvent(evt);
	}
}
