package nl.fontys.s3.erp.business.dtos.announcementdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateAnnouncementResponse {
    private Long id;
}
