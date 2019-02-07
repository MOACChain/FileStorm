package moac.ipfs.modules.back.storagePackage.dao;

import moac.ipfs.modules.back.storagePackage.entity.StoragePackageEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-27 10:19:35
 */
@Mapper
@Repository
public interface StoragePackageDao extends BaseMapper<StoragePackageEntity> {

    List<StoragePackageEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void deleteBatch(Long[] storagePackageIds);

    /**
     * 查询需要更新状态的套餐
     * @return
     */
    List<StoragePackageEntity> queryUpdatePackage();
}
