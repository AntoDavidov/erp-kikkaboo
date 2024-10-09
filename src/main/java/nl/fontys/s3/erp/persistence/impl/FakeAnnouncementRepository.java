package nl.fontys.s3.erp.persistence.impl;

import nl.fontys.s3.erp.persistence.AnnouncementRepository;
import nl.fontys.s3.erp.persistence.entity.AnnouncementEntity;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class FakeAnnouncementRepository implements AnnouncementRepository {
    private static long NEXT_ID = 1;
    private final List<AnnouncementEntity> announcementsEntities;

    public FakeAnnouncementRepository() { this.announcementsEntities = new ArrayList<AnnouncementEntity>(); }

    @Override
    public AnnouncementEntity findById(long id){
        return this.announcementsEntities.stream().filter(entity -> entity.getId() == id).findFirst().orElse(null);

    }

    @Override
    public List<AnnouncementEntity> findAll() {
        return this.announcementsEntities;
    }

    @Override
    public void deleteById(long id){
        this.announcementsEntities.removeIf(entity -> entity.getId() == id);
    }

    @Override
    public AnnouncementEntity save(AnnouncementEntity announcementEntity){
        if(announcementEntity.getId() == null){
            announcementEntity.setId(NEXT_ID);
            NEXT_ID++;
            this.announcementsEntities.add(announcementEntity);
        } else {
            AnnouncementEntity oldEntity = this.findById(announcementEntity.getId());
            if(oldEntity != null){
                oldEntity.setTitle(announcementEntity.getTitle());
                oldEntity.setContent(announcementEntity.getContent());
                oldEntity.setCreatedAt(announcementEntity.getCreatedAt());
                oldEntity.setType(announcementEntity.getType());
                oldEntity.setExpirationDate(announcementEntity.getExpirationDate());
                oldEntity.setDepartment(announcementEntity.getDepartment());
                oldEntity.setCreatedBy(announcementEntity.getCreatedBy());
            }else {
                this.announcementsEntities.add(announcementEntity);
            }

        }
        return announcementEntity;

    }

    @Override
    public boolean existsById(long id){
        return this.announcementsEntities.stream().anyMatch(entity -> entity.getId() == id);
    }

    @Override
    public List<AnnouncementEntity> findByTitle(String title){
        this.announcementsEntities.stream().anyMatch(entity -> entity.getTitle().equals(title));

        if(this.announcementsEntities.isEmpty()){
            return Collections.emptyList();
        }else {
            return this.announcementsEntities;
        }

    }



}
