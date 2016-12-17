package io.jrat.remover.removers;

import io.jrat.remover.Detection;
import io.jrat.remover.Frame;

import java.util.List;

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
