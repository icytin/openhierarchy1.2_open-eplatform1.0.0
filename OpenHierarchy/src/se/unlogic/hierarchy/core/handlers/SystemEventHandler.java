package se.unlogic.hierarchy.core.handlers;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.log4j.Logger;

import se.unlogic.hierarchy.core.comparators.PriorityComparator;
import se.unlogic.hierarchy.core.enums.EventSource;
import se.unlogic.hierarchy.core.enums.EventTarget;
import se.unlogic.hierarchy.core.interfaces.EventHandler;
import se.unlogic.hierarchy.core.interfaces.EventListener;
import se.unlogic.hierarchy.core.interfaces.GlobalEventListener;
import se.unlogic.standardutils.enums.Order;

/**
 * A generic event handler for sending and listening to events related to specific classes and event types on a global level
 *
 * @author Robert "Unlogic" Olofsson
 *
 */
public class SystemEventHandler implements EventHandler {

	private static final PriorityComparator PRIORITY_COMPARATOR = new PriorityComparator(Order.ASC);

	protected Logger log = Logger.getLogger(this.getClass());

	@SuppressWarnings("rawtypes")
	private ConcurrentHashMap<Class<?>,ConcurrentHashMap<Class<?>, Set<EventListener>>> listenerMap = new ConcurrentHashMap<Class<?>,ConcurrentHashMap<Class<?>, Set<EventListener>>>();

	private ConcurrentSkipListSet<GlobalEventListener> globalEventListeners = new ConcurrentSkipListSet<GlobalEventListener>(PRIORITY_COMPARATOR);

	@Override
	public <E extends Serializable> void sendEvent(Class<?> key, E event, EventTarget eventTarget) {

		sendEvent(key, event, null, eventTarget, EventSource.LOCAL);
	}

	@Override
	public <E extends Serializable> void sendEvent(Class<?> key, E event, Object sender, EventTarget eventTarget) {

		sendEvent(key, event, sender, eventTarget, EventSource.LOCAL);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <E extends Serializable> void sendEvent(Class<?> key, E event, Object sender, EventTarget eventTarget, EventSource eventSource) {

		if(event == null){

			throw new NullPointerException("event cannot be null");
		}

		if(eventTarget == null){

			throw new NullPointerException("targetScope cannot be null");
		}

		if(eventSource == null){

			throw new NullPointerException("sourceScope cannot be null");
		}

		if(eventTarget.isLocal()){

			ConcurrentHashMap<Class<?>, Set<EventListener>> eventTypeMap = listenerMap.get(key);

			if(eventTypeMap != null){

				Set<EventListener> eventListeners = eventTypeMap.get(event.getClass());

				if(eventListeners != null){

					for(EventListener eventListener : eventListeners){

						if(sender != null && sender == eventListener){

							continue;
						}

						try{
							eventListener.processEvent(event, eventSource);

						}catch (Throwable e){

							log.error("Error notifying event listener " + eventListener + " regarding event for class " + key.getClass() + " and event type " + event.getClass(), e);
						}
					}
				}
			}
		}

		for(GlobalEventListener globalEventListener : this.globalEventListeners){

			try {
				if(sender != null && sender == globalEventListener){

					continue;
				}

				globalEventListener.processEvent(key, event, eventTarget, eventSource);

			} catch (Throwable e) {

				log.error("Error notifying global event listener " + globalEventListener + " regarding event for class " + event.getClass() + " and event type " + event.getClass(), e);
			}
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public  <L extends Serializable, E extends L> boolean addEventListener(Class<?> key, Class<E> eventType, EventListener<L> listener){

		ConcurrentHashMap<Class<?>, Set<EventListener>> eventTypeMap = listenerMap.get(key);

		Set<EventListener> eventListeners;

		if(eventTypeMap == null){

			eventTypeMap = new ConcurrentHashMap<Class<?>, Set<EventListener>>();
			listenerMap.put(key, eventTypeMap);

			eventListeners = new ConcurrentSkipListSet<EventListener>(PRIORITY_COMPARATOR);
			eventTypeMap.put(eventType, eventListeners);

		}else{

			eventListeners = eventTypeMap.get(eventType);

			if(eventListeners == null){

				eventListeners = new ConcurrentSkipListSet<EventListener>(PRIORITY_COMPARATOR);
				eventTypeMap.put(eventType,eventListeners);
			}
		}

		return eventListeners.add(listener);
	}

	@Override
	public  <L extends Serializable, E extends L> void addEventListener(Class<E> eventType, EventListener<L> listener, Class<?>... keys){

		for(Class<?> key : keys){

			addEventListener(key, eventType, listener);
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public <L extends Serializable, E extends L> boolean removeEventListener(Class<?> key, Class<E> eventType, EventListener<L> listener){

		ConcurrentHashMap<Class<?>, Set<EventListener>> eventTypeMap = listenerMap.get(key);

		if(eventTypeMap != null){

			Set<EventListener> eventListeners = eventTypeMap.get(eventType);

			if(eventListeners != null){

				if(eventListeners.remove(listener)){

					if(eventListeners.isEmpty()){

						eventTypeMap.remove(eventType);

						if(eventTypeMap.isEmpty()){

							listenerMap.remove(key);
						}
					}

					return true;
				}
			}
		}

		return false;
	}

	@Override
	public <L extends Serializable, E extends L> void removeEventListener(Class<E> eventType, EventListener<L> listener, Class<?>... keys) {

		for(Class<?> key : keys){

			removeEventListener(key, eventType, listener);
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void removeEventListener(EventListener<?> listener) {

		Iterator<ConcurrentHashMap<Class<?>, Set<EventListener>>> listenerMapIterator = listenerMap.values().iterator();

		while(listenerMapIterator.hasNext()){

			ConcurrentHashMap<Class<?>, Set<EventListener>> eventTypeMap = listenerMapIterator.next();

			Iterator<Set<EventListener>> eventTypeMapIterator = eventTypeMap.values().iterator();

			while(eventTypeMapIterator.hasNext()){

				Set<EventListener> eventListeners = eventTypeMapIterator.next();

				if(eventListeners.remove(listener)){

					if(eventListeners.isEmpty()){

						eventTypeMapIterator.remove();

						if(eventTypeMap.isEmpty()){

							listenerMapIterator.remove();
						}
					}
				}
			}
		}
	}

	@Override
	public boolean addGlobalEventListener(GlobalEventListener listener) {

		return this.globalEventListeners.add(listener);
	}

	@Override
	public boolean removeGlobalEventListener(GlobalEventListener listener) {

		return this.globalEventListeners.remove(listener);
	}

	@SuppressWarnings("rawtypes")
	public synchronized void clear(){

		for(Entry<Class<?>,ConcurrentHashMap<Class<?>, Set<EventListener>>> entry : listenerMap.entrySet()){

			for(Entry<Class<?>, Set<EventListener>> eventTypeEntry : entry.getValue().entrySet()){

				for(EventListener listener : eventTypeEntry.getValue()){

					log.warn("Event listener for type " + entry.getKey() + " and event " + eventTypeEntry.getKey() + " implemented by " + listener.getClass() + " not removed from event handler on shutdown.");
				}
			}
		}

		this.listenerMap.clear();

		for(GlobalEventListener listener : globalEventListeners){

			log.warn("Global event listener implemented by " + listener.getClass() + " not removed from event handler on shutdown.");
		}

		this.globalEventListeners.clear();
	}
}
