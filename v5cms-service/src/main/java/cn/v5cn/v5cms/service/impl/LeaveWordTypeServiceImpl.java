package cn.v5cn.v5cms.service.impl;

import cn.v5cn.v5cms.dao.LeaveWordTypeMapper;
import cn.v5cn.v5cms.entity.LeaveWordType;
import cn.v5cn.v5cms.service.LeaveWordTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZXF-PC1 on 2015/7/28.
 */
@Service("leaveWordTypeService")
public class LeaveWordTypeServiceImpl implements LeaveWordTypeService {

    @Autowired
    private LeaveWordTypeMapper leaveWordTypeMapper;

    /*@Override
    public Page<LeaveWordType> findLeaveWordTypePageable(final LeaveWordType leaveWordType, Integer currPage) {
        int pageSize = Integer.valueOf(PropertyUtils.getValue("page.size").or("0"));

        if(currPage == null || currPage < 1) currPage = 1;

        return leaveWordTypeDao.findAll(new Specification<LeaveWordType>(){
            @Override
            public Predicate toPredicate(Root<LeaveWordType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> ps = Lists.newArrayList();
                if(leaveWordType.getName() !=null && !"".equals(leaveWordType.getName())){
                    Path<String> name = root.get("name");
                    Path<String> lwdes = root.get("lwdes");

                    ps.add(criteriaBuilder.like(name, "%" + leaveWordType.getName() + "%"));
                    ps.add(criteriaBuilder.like(lwdes, "%" + leaveWordType.getName() + "%"));
                }

                //criteriaBuilder.conjunction();  创建一个AND
                //criteriaBuilder.disjunction();  创建一个OR
                return ps.size() == 0 ? criteriaBuilder.conjunction():criteriaBuilder.or(ps.toArray(new Predicate[ps.size()]));
            }
        },new PageRequest(currPage-1,pageSize,new Sort(Sort.Direction.DESC,"id")));
    }*/
}
