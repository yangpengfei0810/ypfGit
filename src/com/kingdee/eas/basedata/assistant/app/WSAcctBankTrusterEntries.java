package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.webservice.WSBean;

public class WSAcctBankTrusterEntries extends WSBean { 
    private int seq ;

    private com.kingdee.eas.basedata.assistant.app.WSAccountBank acctBank ;

    private String id ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit trustCompany ;

    public int getSeq() {
        return this.seq;
    }

    public void setSeq( int seq) {
        this.seq = seq;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAccountBank getAcctBank() {
        return this.acctBank;
    }

    public void setAcctBank( com.kingdee.eas.basedata.assistant.app.WSAccountBank acctBank) {
        this.acctBank = acctBank;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getTrustCompany() {
        return this.trustCompany;
    }

    public void setTrustCompany( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit trustCompany) {
        this.trustCompany = trustCompany;
    }

}