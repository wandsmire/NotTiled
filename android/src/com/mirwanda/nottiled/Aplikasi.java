package com.mirwanda.nottiled;

import android.app.*;
import org.solovyev.android.checkout.*;

public class Aplikasi extends Application
{

    private static Aplikasi sInstance;

    private final Billing mBilling = new Billing(this, new Billing.DefaultConfiguration() {
			@Override
			public String getPublicKey() {
				return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtA/2kYsPa1PiDfK8F8nPsu+r5+3vfc8WP/I/KWaeGDIXpYyY8Eeqf4wcgWc4tYDM3OmGf0mKIJAoY+c9CTOGvRAkmczODfJUldxMhoCzyInyidSd7t2Timzpkqc69xQgrQFMnsyS+/T3RCocrhtyKqURbjL4rXu4LYcWDga0ZziyYu2roowJQ//1LiIbHxW/K09e8WfROrrT5gzo1WzFLC+mBAPYe8G4MFsixYcct5KytZ0pDkov2VeVuTrsiYy6EoW8iDPspk+c7tuV3beJH7XPvwA3rgwCUj/mOZCtKFInDg3AHkAcILIdo1Lkpt7boceRH3MHd+W89n6VpquF6QIDAQAB";
			}
		});

    public Aplikasi() {
        sInstance = this;
    }



    public static Aplikasi get() {
        return sInstance;
    }

    public Billing getBilling() {
        return mBilling;
    }
}

