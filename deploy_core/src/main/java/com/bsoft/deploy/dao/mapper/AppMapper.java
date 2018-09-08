package com.bsoft.deploy.dao.mapper;

import com.bsoft.deploy.dao.entity.App;
import com.bsoft.deploy.dao.entity.AppPackage;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 应用mapper
 * Created on 2018/8/23.
 *
 * @author yangl
 */
@Mapper
public interface AppMapper {

    /**
     * 保存基础应用
     * @param app
     */
    @Insert("insert into base_app(appName,path,describes) values(#{appName},#{path},#{describes})")
    @Options(useGeneratedKeys = true, keyProperty = "appId", keyColumn = "appId")
    void save(App app);

    @Update("update base_app set appName=#{appName},path=#{path},describes=#{describes} where appId=#{appId}")
    void update(App app);

    @Delete("delete from base_app where appId=#{appId}")
    void delete(int appId);

    @Select("select appId,appName,path,describes from base_app")
    List<App> loadApps();

    @Select("select appId,appName,path,describes from base_app where appId=#{appId}")
    App findApp(int appId);

    @Select("select id,appId,version,updateDesc,optime,type,locked from app_upd_pkg where appId=#{appId} order by id desc")
    List<AppPackage> loadAppPackages(int appId);

    @Select("select id,appId,version,updateDesc,optime,type,locked from app_upd_pkg where id=#{id}")
    AppPackage findAppPackageById(int id);

    @Insert("insert into app_upd_pkg(appId,version,updateDesc,optime,type,locked) values(#{appId},#{version},#{updateDesc},#{optime, jdbcType=TIMESTAMP},#{type},#{locked})")
    @Options(useGeneratedKeys = true)
    void saveAppPackage(AppPackage appPackage);

    @Update("update app_upd_pkg set version=#{version},updateDesc=#{updateDesc},type=#{type},locked=#{locked} where id=#{id}")
    void updateAppPackage(AppPackage appPackage);

    @Select("select count(id) from app_upd_pkg where appId=#{appId} and id>#{pkgId}")
    int hasUpdateVersion(@Param("appId") int appId,@Param("pkgId") int pkgId);
}
