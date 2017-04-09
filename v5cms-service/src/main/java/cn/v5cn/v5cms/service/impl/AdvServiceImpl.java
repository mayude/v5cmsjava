package cn.v5cn.v5cms.service.impl;

import cn.v5cn.v5cms.dao.AdvMapper;
import cn.v5cn.v5cms.service.AdvService;
import cn.v5cn.v5cms.entity.Adv;
import cn.v5cn.v5cms.entity.Advpos;
import cn.v5cn.v5cms.util.SystemUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by ZYW on 2014/7/28.
 */
@Service("advService")
public class AdvServiceImpl implements AdvService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdvServiceImpl.class);

    @Autowired
    private AdvMapper advMapper;

    @Override
    @Transactional
    public Adv save(Object advWrapper) throws JsonProcessingException {
//        Adv adv = advWrapper.getAdv();
//        String advTypeJson = advTypeJson(advWrapper.getAti(), adv.getAdvType());
//
//        adv.setAdvTypeInfo(advTypeJson);
//        adv.setCreateDate(DateTime.now().toDate());
        return null;//advDao.save(adv);
    }

    @Override
    @Transactional
    public Adv update(Object advWrapper) throws JsonProcessingException
    ,IllegalAccessException, InvocationTargetException {
//        Adv fontAdv = advWrapper.getAdv();
//        String advTypeJson = advTypeJson(advWrapper.getAti(), fontAdv.getAdvType());
//        fontAdv.setAdvTypeInfo(advTypeJson);

        Adv dbAdv = null;//advDao.findOne(fontAdv.getAdvId());
//        SystemUtils.copyProperties(dbAdv,fontAdv);
        return dbAdv;
    }

    @Override
    public void deleteAdvs(Long[] advIds,String realPath) {
        List<Adv> deleteAdvs = null;//Lists.newArrayList(advDao.findAll(Lists.newArrayList(advIds)));
        //删除图片或Flash资源
//        String realPath = HttpUtils.getRealPath(request, SystemConstant.ADV_RES_PATH);
        ObjectMapper objectMapper = new ObjectMapper();
        for(Adv adv : deleteAdvs){
//            if(adv.getAdvType() == 3 || adv.getAdvType() == 4){
//                continue;
//            }
            try {
                Map<String,String> advTypeInfo = null;//objectMapper.readValue(adv.getAdvTypeInfo(),Map.class);
                if(advTypeInfo.containsKey("adv_image_url") && StringUtils.isNotBlank(advTypeInfo.get("adv_image_url"))){
                    String fileName = FilenameUtils.getName(advTypeInfo.get("adv_image_url"));
                    boolean result = FileUtils.deleteQuietly(new File(realPath+"/"+fileName));
                    if(!result){
                        LOGGER.warn("删除图片资源失败，图片名称{}",fileName);
                    }
                }
                if(advTypeInfo.containsKey("adv_flash_url") && StringUtils.isNotBlank(advTypeInfo.get("adv_flash_url"))){
                    String fileName = FilenameUtils.getName(advTypeInfo.get("adv_flash_url"));
                    boolean result = FileUtils.deleteQuietly(new File(realPath+fileName));
                    if(!result){
                        LOGGER.warn("删除Flash资源失败，Flash名称{}",fileName);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("转换JSON字符串到Map异常{}",e.getMessage());
                throw new RuntimeException("转换JSON字符串到Map异常",e);
            }
        }

//        advDao.delete(deleteAdvs);
    }

    /*@Override
    public Page<Adv> findAdvByAdvNamePageable(final Adv adv, Integer currPage) {
        int pageSize = Integer.valueOf(PropertyUtils.getValue("page.size").or("0"));

        return advDao.findAll(new Specification<Adv>() {

            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root<Adv> advRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Adv,AdvPos> advPos = advRoot.join("advPos", JoinType.INNER);
                List<javax.persistence.criteria.Predicate> ps = Lists.newArrayList();
                if(StringUtils.isNotBlank(adv.getAdvName())){
                    Path<String> advName = advRoot.get("advName");
                    ps.add(criteriaBuilder.like(advName, "%" + adv.getAdvName() + "%"));
                }
                if(adv.getAdvPos()!= null && adv.getAdvPos().getAdvPosId() != null){
                    Path<Integer> advPosId = advPos.get("advPosId");
                    ps.add(criteriaBuilder.equal(advPosId, adv.getAdvPos().getAdvPosId()));
                }
                return criteriaBuilder.and(ps.toArray(new javax.persistence.criteria.Predicate[0]));
            }
        }, new PageRequest(currPage-1,pageSize,new Sort(Sort.Direction.DESC,"createDate")));
    }*/

    @Override
    public Adv findOne(Long advId) {
        return null;//advDao.findOne(advId);
    }

    private Map<String,Object> filterMapKeys(Map<String, Object> reqMap,final String key) {
        Map<String, Object> filterAfterMap = Maps.filterKeys(reqMap, new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return StringUtils.contains(input,key);
            }
        });
        return filterAfterMap;
    }
    private String advTypeJson(Map<String,Object> reqMap, int advType) throws JsonProcessingException {
        Map<String,Object> filterAfterMap = null;
        if(advType == 1){
            filterAfterMap = filterMapKeys(reqMap,"adv_image_");
        }else if(advType == 2){
            filterAfterMap = filterMapKeys(reqMap,"adv_flash_");
        }else if(advType == 3){
            filterAfterMap = filterMapKeys(reqMap,"adv_text_");
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(filterAfterMap);
    }
}
