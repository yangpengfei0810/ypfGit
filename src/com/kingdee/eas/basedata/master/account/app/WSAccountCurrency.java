package com.kingdee.eas.basedata.master.account.app;

import com.kingdee.bos.webservice.WSBean;

public class WSAccountCurrency extends WSBean { 
    private String id ;

    private com.kingdee.eas.basedata.assistant.app.WSCurrency currency ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountView accountView ;

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCurrency getCurrency() {
        return this.currency;
    }

    public void setCurrency( com.kingdee.eas.basedata.assistant.app.WSCurrency currency) {
        this.currency = currency;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccountView getAccountView() {
        return this.accountView;
    }

    public void setAccountView( com.kingdee.eas.basedata.master.account.app.WSAccountView accountView) {
        this.accountView = accountView;
    }

}