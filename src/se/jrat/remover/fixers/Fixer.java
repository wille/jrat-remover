package se.jrat.remover.fixers;

public abstract interface Fixer {

	/**
	 * 
	 * @param dryrun If we should only check for detections and not clean
	 */
	public abstract void perform(boolean dryrun);
}
