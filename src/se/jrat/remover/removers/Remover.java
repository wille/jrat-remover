package se.jrat.remover.removers;

import se.jrat.remover.Frame;

public abstract class Remover {
	
	protected Frame frame;
	
	public Remover(Frame frame) {
		this.frame = frame;
	}

	/**
	 * 
	 * @param dryrun If we should only check for detections and not clean
	 */
	public abstract void perform(boolean dryrun);
}
