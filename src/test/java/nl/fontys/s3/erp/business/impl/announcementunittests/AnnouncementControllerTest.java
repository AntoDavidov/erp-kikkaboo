package nl.fontys.s3.erp.business.impl.announcementunittests;

import nl.fontys.s3.erp.business.announcementsusecases.*;
import nl.fontys.s3.erp.business.dtos.announcementdto.GetAnnouncementsResponse;
import nl.fontys.s3.erp.domain.announcements.Announcement;
import nl.fontys.s3.erp.domain.announcements.AnnouncementType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = {"CEO", "MANAGER"})
public class AnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteAnnouncementUseCase deleteAnnouncementUseCase;
    @MockBean
    private GetAnnouncementUseCase getAnnouncementUseCase;
    @MockBean
    private GetAllAnnouncementsUseCase getAllAnnouncementsUseCase;

    @Test
    //put withmockUser on every test
    public void shouldReturnAllAnnouncements() throws Exception {
        GetAnnouncementsResponse response = new GetAnnouncementsResponse(
                Collections.singletonList(new Announcement(1L, "Title", "Content", null, null, null, null, AnnouncementType.DEPARTMENTAL, true))
        );
        when(getAllAnnouncementsUseCase.getAllAnnouncements()).thenReturn(response);

        mockMvc.perform(get("/announcements")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.announcements[0].id").value(1))
                .andExpect(jsonPath("$.announcements[0].title").value("Title"))
                .andExpect(jsonPath("$.announcements[0].content").value("Content"));
    }
//    @Test
//    //either use repositoers use real data ORRRRR mock  businesslayer
//    //check alternatives
//    public void shouldCreateAnnouncement() throws Exception {
//
//        CreateAnnouncementRequest request = new CreateAnnouncementRequest("Title", "Content", null, null, null, null, AnnouncementType.DEPARTMENTAL, true);
//        CreateAnnouncementResponse response = new CreateAnnouncementResponse(1L);
//        when(createAnnouncementUseCase.createAnnouncement(any(CreateAnnouncementRequest.class))).thenReturn(response);
//
//        mockMvc.perform(post("/announcements")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{ \"title\": \"New Title\", \"content\": \"New Content\" }"))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(1));
//    }
    @Test
    public void shouldDeleteAnnouncement() throws Exception {
        // Arrange
        doNothing().when(deleteAnnouncementUseCase).deleteAnnouncement(1);

        // Act & Assert
        mockMvc.perform(delete("/announcements/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Test
    public void shouldReturnSingleAnnouncement() throws Exception {
        // Arrange
        Announcement announcement = new Announcement(1L,"Title", "Content", null, null, null, null, AnnouncementType.DEPARTMENTAL, true);
        when(getAnnouncementUseCase.getAnnouncement(1L)).thenReturn(announcement);

        // Act & Assert
        mockMvc.perform(get("/announcements/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.content").value("Content"));
    }
//    @Test
//    public void shouldUpdateAnnouncement() throws Exception {
//        // Arrange
//        doNothing().when(updateAnnouncementUseCase).updateAnnouncement(any(UpdateAnnouncementRequest.class));
//
//        // Act & Assert
//        mockMvc.perform(put("/announcements/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{ \"title\": \"Updated Title\", \"content\": \"Updated Content\" }"))
//                .andExpect(status().isNoContent());
//    }
}
