package com.arxanfintech.core.crypto.cryptohash;

public class Keccak256 extends KeccakCore {

	/**
	 * Create the engine.
	 */
	public Keccak256()
	{
	}

	/** @see com.arxanfintech.core.crypto.cryptohash.Digest */
	public Digest copy()
	{
		return copyState(new Keccak256());
	}

	/** @see com.arxanfintech.core.crypto.cryptohash.Digest */
	public int getDigestLength()
	{
		return 32;
	}
}
