package com.centit.framework.system.dao.hibernateimpl;

import com.centit.framework.core.dao.CodeBook;
import com.centit.framework.hibernate.dao.BaseDaoImpl;
import com.centit.framework.system.dao.DataCatalogDao;
import com.centit.framework.system.po.DataCatalog;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("dataCatalogDao")
public class DataCatalogDaoImpl extends BaseDaoImpl<DataCatalog, String> implements DataCatalogDao {

    @Transactional
    public List<DataCatalog> listFixCatalog() {
        return listObjects("FROM DataCatalog WHERE catalogStyle='F'");

    }

    @Transactional
    public List<DataCatalog> listUserCatalog() {
        return listObjects("FROM DataCatalog WHERE catalogStyle='U'");
    }

    @Transactional
    public List<DataCatalog> listSysCatalog() {
        return listObjects("FROM DataCatalog WHERE catalogStyle='S'");

    }

    // 规定在List事件中只能通过这三个字段进行查询
    @Override
    public Map<String, String> getFilterField() {
        if (filterField == null) {
            filterField = new HashMap<>();
            filterField.put("catalogCode", CodeBook.LIKE_HQL_ID);
            filterField.put("catalogName", CodeBook.LIKE_HQL_ID);
            filterField.put("catalogStyle", CodeBook.EQUAL_HQL_ID);
            filterField.put("catalogType", CodeBook.EQUAL_HQL_ID);
            filterField.put("optId", CodeBook.EQUAL_HQL_ID);
        }
        return filterField;
    }

    @Override
    public void updateCatalog(DataCatalog dataCatalog){
        super.updateObject(dataCatalog);
    }
}
