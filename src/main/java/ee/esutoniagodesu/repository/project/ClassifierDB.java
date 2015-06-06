package ee.esutoniagodesu.repository.project;

import ee.esutoniagodesu.pojo.entity.IntIDStringTitle;
import ee.esutoniagodesu.util.persistence.JDBCUtil;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.List;

@Repository
public class ClassifierDB extends AbstractProjectRepository {

    public <T extends IntIDStringTitle> T save(Classifier cf, String title, String descr, String com, int order, int parentId) {

        StringBuilder msg = new StringBuilder("insert: title=" + title +
            ", descr=" + descr +
            ", com=" + com +
            ", order=" + order +
            ", parentId=" + parentId);

        if (title == null || title.length() < 1 || order < 0 || parentId < 0)
            throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        CallableStatement s = null;
        int newId = 0;

        try {
            con = dao.getConnection();

            String sql = "{? = call f_ins_classifier(?,?,?, ?,?,?)}";
            s = con.prepareCall(sql);
            s.registerOutParameter(1, Types.INTEGER);

            s.setString(2, cf.name().toLowerCase());
            s.setString(3, title);
            if (descr != null && descr.length() > 0) s.setString(4, descr);
            else s.setNull(4, Types.VARCHAR);
            if (com != null && com.length() > 0) s.setString(5, com);
            else s.setNull(5, Types.VARCHAR);
            if (order > 0) s.setInt(6, order);
            else s.setNull(6, Types.INTEGER);

            if (parentId > 0) s.setInt(7, parentId);
            else s.setNull(7, Types.INTEGER);

            s.execute();

            newId = s.getInt(1);

            Assert.isTrue(newId > 0);

            log.debug(msg.append(", newId=").append(newId));

        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(s, con);
        }

        return find(cf, newId);
    }

    public <T extends IntIDStringTitle> List<T> findAll(Classifier cf) {
        return dao.findAll(cf.CLAZZ);
    }

    public <T extends IntIDStringTitle> T find(Classifier cf, Serializable id) {
        return (T) dao.find(cf.CLAZZ, id);
    }
}

