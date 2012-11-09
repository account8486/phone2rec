package com.wondertek.meeting.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.MeetingFilesDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingFiles;
import com.wondertek.meeting.service.MeetingFilesService;
import com.wondertek.meeting.util.StringUtil;

public class MeetingFilesServiceImpl extends
        BaseServiceImpl<MeetingFiles, Integer> implements MeetingFilesService {

    private MeetingFilesDao meetingFilesDao;


    public void setMeetingFilesDao(MeetingFilesDao meetingFilesDao) {
        this.basicDao = meetingFilesDao;
        this.meetingFilesDao = meetingFilesDao;
    }

    public List<MeetingFiles> getMeetingFilesList(MeetingFiles meetingFiles,Integer memberLevel)
            throws HibernateDaoSupportException {

        List<MeetingFiles> meetingFilesList = new ArrayList<MeetingFiles>();
        StringBuffer hql = new StringBuffer();
        hql.append("  FROM MeetingFiles meetingFiles where 1=1 ");

        if (meetingFiles.getMeetingId() != null
                && !"".equals(meetingFiles.getMeetingId())) {
        	
            hql.append(" and meetingFiles.meetingId="
                    + meetingFiles.getMeetingId());
        }
        
        if(meetingFiles.getAssortId()!=null&&!"".equals(meetingFiles.getAssortId())){
        	 hql.append(" and meetingFiles.assortId="
                     + meetingFiles.getAssortId());
        }
        
        //如果未传值  就不做限制
        if(StringUtil.isNumber(String.valueOf(memberLevel))){
        	 hql.append(" and (fileAccess is null or fileAccess='' or fileAccess like '%"+memberLevel+"%')");
        }
        
        
        
        
        //倒序排列
        hql.append(" order by (case when sortCode is null then 999999 else sortCode end)  asc,meetingFiles.createTime desc ");
        
        meetingFilesList = meetingFilesDao.getObjects(hql.toString());

        return meetingFilesList;
        
    }

    public List<MeetingFiles> getUnconvertMeetingFilesList() throws HibernateDaoSupportException {
        List<MeetingFiles> meetingFilesList = new ArrayList<MeetingFiles>();
        String hql = " from MeetingFiles meetingFiles where state = 0 or state is null";
        Pager<MeetingFiles> pager = meetingFilesDao.findPager(hql.toString(), 1, 10, null);

        return pager.getPageRecords();
    }

    /**
     * 获取分页信息
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Pager<MeetingFiles> getMeetingFilesPager(int currentPage,
                                                    int pageSize, MeetingFiles meetingFiles,String memberLevel) {

        Pager<MeetingFiles> pager = null;
        StringBuffer hql = new StringBuffer();
        Map<String, Object> properties = new HashMap<String, Object>();
        hql.append("  from MeetingFiles  where 1=1 ");
        if (meetingFiles.getMeetingId() != null) {
            hql.append(" and meetingId=" + meetingFiles.getMeetingId());
        }

        /**
         * 文件名来查询
         */
        if (meetingFiles.getFileName() != null && !"".equals(meetingFiles.getFileName())) {
            hql.append(" and fileName like '%" + meetingFiles.getFileName() + "%'");
        }
        
        if (meetingFiles.getAssortId() != null && !"".equals(meetingFiles.getAssortId())) {
        	  hql.append(" and assortId =" + meetingFiles.getAssortId());
        }
        //如果未传值  就不做限制
        if(StringUtil.isNotEmpty(memberLevel)){
        	 hql.append(" and (fileAccess is null or fileAccess='' or fileAccess like '%"+memberLevel+"%')");
        }
        
        
        
        //倒序排列
        hql.append(" order by (case when sortCode is null then 999999 else sortCode end)  asc,createTime desc,assortId asc ");

        try {
            pager = meetingFilesDao.findPager(hql.toString(), currentPage,
                    pageSize, null);
        } catch (HibernateDaoSupportException e) {
            e.printStackTrace();
        }
        return pager;

    }

    public void saveMeetingFiles(MeetingFiles meetingFiles)
            throws HibernateDaoSupportException {
        meetingFilesDao.add(meetingFiles);
    }
    
    /**
	 * 删除会议下资料
	 * @param meetingId
	 * @throws ServiceException 
	 */
	public void deleteMeetingFilesByMeetingId(Long meetingId) throws ServiceException{
		meetingFilesDao.deleteMeetingFilesByMeetingId(meetingId);
	}

	@Override
	public List<MeetingFiles> findMeetingFiles(Long meetingId) throws HibernateDaoSupportException {
		String hql = " from MeetingFiles mf  where mf.state = 0  and mf.meetingId="+meetingId;
		return meetingFilesDao.getObjects(hql);
	}
	



}
