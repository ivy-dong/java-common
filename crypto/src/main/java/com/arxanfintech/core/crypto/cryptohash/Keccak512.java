package com.arxanfintech.core.crypto.cryptohash;

public class Keccak512 extends KeccakCore {

	/**
	 * Create the engine.
	 */
	public Keccak512()
	{
	}

	/** @see Digest */
	public Digest copy()
	{
		return copyState(new Keccak512());
	}

	/** @see Digest */
	public int getDigestLength()
	{
		return 64;
	}
}
