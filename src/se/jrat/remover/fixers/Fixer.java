package se.jrat.remover.fixers;

import se.jrat.remover.Frame;

public abstract class Fixer {
	
	protected Frame frame;
	
	public Fixer(Frame frame) {
		this.frame = frame;
	}

	/**
	 * 
	 * @param dryrun If we should only check for detections and not clean
	 */
	public abstract void perform(boolean dryrun);
}
