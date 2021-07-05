package use_case;

import entity.Vip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VipManager implements Serializable {

    /**
     * Represents the UserManager that manages this Vip
     */
    UserManager userManager;
    public List<String> vipIdList;
    public List<String> vipCodeList;

    /**
     * Represents the VipManager that manages this Vip.
     */
    public VipManager(UserManager um) {
        userManager = um;
        vipIdList = new ArrayList<>();
        vipCodeList = new ArrayList<>();
    }

    /**
     * Creates a new Vip with the specified name, id, password, and adds new Vip to userList.
     * @param name The name of this Vip.
     * @param id The id of this Vip.
     * @param pw The password of this Vip.
     */

    public boolean newVip(String name, String id, String pw, String code) {
        Vip vip = new Vip(name, id, pw, code);
        userManager.userList.add(vip);
        vipIdList.remove(id);
        vipCodeList.remove(code);
        return true;
    }
    /**
     * Creates a new Vip with the specified name, id, password, and adds new Vip to userList.
     * @param name The name of this Vip.
     * @param id The id of this Vip.
     * @param pw The password of this Vip.
     * For organizerController purpose only.
     */

    public boolean newVip(String name, String id, String pw) {
        String code;
        if (this.vipCodeList.isEmpty()) {
            code = "a";
        } else {
            code = this.vipCodeList.get(0) + "a";
        }
        Vip vip = new Vip(name, id, pw, code);
        userManager.userList.add(vip);
        vipIdList.remove(id);
        vipCodeList.remove(code);
        return true;
    }

    /**
     * Checks to see if Vip is enrolled in specified event.
     * @param event The name of the event.
     */
    public boolean isEnrolled(String event) {
        Vip vip = (Vip) userManager.userIded(userManager.getCurrentUserId());
        return vip.isInAttendingEvents(event);
    }

    public boolean isValidCode(String id, String code){
        if (!(vipIdList.contains(id) && vipCodeList.contains(code))){
            return false;}
        String validCode = vipCodeList.get(vipIdList.indexOf(id));
        if (!validCode.equals(code)){
            return false;}
        return true;
    }

    public boolean addNewCode(String id, String code){
        if (vipIdList.contains(id) || vipCodeList.contains(code)){
            return false;
        }
        vipIdList.add(id);
        vipCodeList.add(code);
        return true;
    }

}
