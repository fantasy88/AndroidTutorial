
package com.example.readcontact;

public class UserContacts {
    private String name;
    private String phone;
    private boolean selected;
    
    public UserContacts(String name,String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
