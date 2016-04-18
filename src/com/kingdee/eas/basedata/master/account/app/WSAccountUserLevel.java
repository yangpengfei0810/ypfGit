package com.kingdee.eas.basedata.master.account.app;

import com.kingdee.bos.webservice.WSBean;

public class WSAccountUserLevel extends WSBean { 
    private com.kingdee.eas.basedata.master.account.app.WSAccountTable accountTable ;

    private int level ;

    private int length ;

    private String id ;

    public com.kingdee.eas.basedata.master.account.app.WSAccountTable getAccountTable() {
        return this.accountTable;
    }

    public void setAccountTable( com.kingdee.eas.basedata.master.account.app.WSAccountTable accountTable) {
        this.accountTable = accountTable;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel( int level) {
        this.level = level;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength( int length) {
        this.length = length;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

}