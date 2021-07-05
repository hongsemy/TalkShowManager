package entity;

import java.io.Serializable;

/**
 * Represents a VIP of the conference. A VIP is a special vip of the program.
 * VIPs are able to do the following:
 * - attend VIP-exclusive events
 * - send private messages to the speakers of the events they are signed up for.
 */
public class Vip extends Attendee implements Serializable {

    public String code;
    /**
     * Creates a new Vip with the specified name, id, and password.
     *
     * @param name The name of this Vip.
     * @param id   The id of this Vip.
     * @param pw   The password of this Vip.
     */
    public Vip(String name, String id, String pw, String code) {
        super(name, id, pw);
        this.code = code;
    }

}

