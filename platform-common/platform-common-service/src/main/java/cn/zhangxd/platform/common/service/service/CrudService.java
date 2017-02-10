package cn.zhangxd.platform.common.service.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.zhangxd.platform.common.api.DataEntity;
import cn.zhangxd.platform.common.api.Paging;
import cn.zhangxd.platform.common.service.dao.CrudDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Service基类
 *
 * @param <D> the type parameter
 * @param <T> the type parameter
 * @author zhangxd
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity> {

    /**
     * 持久层对象
     */
    @Autowired
    private D dao;

    public D getDao() {
        return dao;
    }

    /**
     * 获取单条数据
     *
     * @param id 主键
     * @return 数据实体
     */
    public T get(String id) {
        return dao.get(id);
    }

    /**
     * 获取单条数据
     *
     * @param entity 实体对象
     * @return 实体对象
     */
    public T get(T entity) {
        return dao.get(entity);
    }

    /**
     * 查询列表数据
     *
     * @param entity 实体对象
     * @return 实体对象列表
     */
    public List<T> findList(T entity) {
        return dao.findList(entity);
    }

    /**
     * 查询列表数据
     *
     * @param queryMap 查询条件
     * @return 实体对象列表
     */
    public List<T> queryList(Map<String, Object> queryMap) {
        return dao.queryList(queryMap);
    }

    /**
     * 查询分页数据
     *
     * @param page   分页对象
     * @param entity 实体对象
     * @return 分页数据
     */
    public PageInfo<T> findPage(Paging page, T entity) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<T> list = dao.findList(entity);
        return new PageInfo<>(list);
    }

    /**
     * 查询分页数据
     *
     * @param page     分页对象
     * @param queryMap 查询条件
     * @return 分页数据
     */
    public PageInfo<T> queryPage(Paging page, Map<String, Object> queryMap) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<T> list = dao.queryList(queryMap);
        return new PageInfo<>(list);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param entity 实体对象
     * @return 实体对象
     */
    @Transactional(readOnly = false)
    public T save(T entity) {
        if (entity.getIsNewRecord()) {
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
        }
        return entity;
    }

    /**
     * 删除数据
     *
     * @param entity 实体对象
     */
    @Transactional(readOnly = false)
    public void delete(T entity) {
        dao.delete(entity);
    }

    /**
     * 删除数据
     *
     * @param id 主键
     */
    @Transactional(readOnly = false)
    public void deleteById(String id) {
        dao.deleteById(id);
    }

}
