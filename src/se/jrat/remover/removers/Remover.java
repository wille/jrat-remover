package se.jrat.remover.removers;

import java.util.List;

import se.jrat.remover.Detection;
import se.jrat.remover.Frame;

public abstract class Remover {
	
	protected Frame frame;
	
	public Remover(Frame frame) {
		this.frame = frame;
	}

	/**
	 * 
	 * @param dryrun If we should only check for detections and not clean
	 * @return list of detections
	 */
	public abstract List<Detection> perform(boolean dryrun);
}
