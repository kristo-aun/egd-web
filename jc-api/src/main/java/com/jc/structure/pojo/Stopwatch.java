package com.jc.structure.pojo;

/**
 * Copyright (c) 2005, Corey Goldberg
 * <p/>
 * JCStopWatch.java is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 */
public final class Stopwatch {

	private boolean running = false;
	private long startTime = 0;
	private long stopTime = 0;

	/**
	 * @return elaspsed time in milliseconds
	 */
	public long getElapsedTime() {
		long elapsed;
		if (running) {
			elapsed = (System.currentTimeMillis() - startTime);
		} else {
			elapsed = (stopTime - startTime);
		}
		return elapsed;
	}

	/**
	 * @return elaspsed time in seconds
	 */
	public long getElapsedTimeSecs() {
		long elapsed;
		if (running) {
			elapsed = ((System.currentTimeMillis() - startTime) / 1000);
		} else {
			elapsed = ((stopTime - startTime) / 1000);
		}
		return elapsed;
	}

	/**
	 * starts stopwatch
	 */
	public void start() {
		this.startTime = System.currentTimeMillis();
		this.running = true;
	}

	/**
	 * stops stopwatch
	 */
	public void stop() {
		this.stopTime = System.currentTimeMillis();
		this.running = false;
	}
}