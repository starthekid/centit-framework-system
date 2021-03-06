package com.centit.framework.system.dao.hibernateimpl;

import com.centit.framework.core.dao.CodeBook;
import com.centit.framework.hibernate.dao.BaseDaoImpl;
import com.centit.framework.hibernate.dao.DatabaseOptUtils;
import com.centit.framework.system.dao.InnerMsgDao;
import com.centit.framework.system.po.InnerMsg;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
@Repository("innerMsgDao")
public class InnerMsgDaoImpl extends BaseDaoImpl<InnerMsg, String> implements InnerMsgDao {

    @Override
    public Map<String, String> getFilterField() {
        if (filterField == null) {
            filterField = new HashMap<>();
            filterField.put("msgType", CodeBook.EQUAL_HQL_ID);
            filterField.put("msgTitle", CodeBook.LIKE_HQL_ID);
            filterField.put("msgContent", CodeBook.LIKE_HQL_ID);
            filterField.put("msgStateNot", "msgState != :msgStateNot");
            filterField.put("sender", CodeBook.EQUAL_HQL_ID);
            filterField.put("receive", "msgCode in (select re.mInnerMsg.msgCode as msgCode from InnerMsgRecipient re Where re.receive = :receive)");
            filterField.put(CodeBook.ORDER_BY_HQL_ID, "sendDate desc");
        }
        return filterField;
    }
    /*
     * 新建
     *
     */
    @Override
    @Transactional
    public void saveNewObject(InnerMsg o) {
        String msgCode = DatabaseOptUtils.getNextKeyBySequence(this, "S_MSGCODE",16);
        o.setMsgCode(msgCode);
        super.saveNewObject(o);
//        return msgCode;
    }

    @Override
    public void updateInnerMsg(InnerMsg innerMsg){
        super.updateObject(innerMsg);
    }

    @Override
    public String getNextKey() {
        return DatabaseOptUtils.getNextValueOfSequence(this, "S_MSGCODE");
    }

}
