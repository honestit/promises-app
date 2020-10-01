package honestit.projects.promises.simple.timelapse.impl;

import honestit.projects.promises.simple.timelapse.IncomingPromisesRequest;
import honestit.projects.promises.simple.timelapse.IncomingPromisesResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DefaultTimelapseService unit tests")
class DefaultTimelapseServiceTest {

    private DefaultTimelapseService timelapseService;

    @BeforeEach
    public void prepareTests() {
        timelapseService = new DefaultTimelapseService();
    }

    @Nested
    @DisplayName("DefaultTimelapseService incomingPromises unit tests")
    class IncomingPromises {

        private IncomingPromisesRequest defaultRequest;

        @BeforeEach
        public void prepareTests() {
            defaultRequest = new IncomingPromisesRequest();
        }

        @Test
        @DisplayName("Should return non null response")
        public void shouldReturnNonNullResponse() {
            IncomingPromisesResponse response = timelapseService.incomingPromises(defaultRequest);

            Assertions.assertThat(response).isNotNull();
        }

        @Test
        @DisplayName("Should return not null promises list")
        public void shouldReturnNotNullPromisesList() {
            IncomingPromisesResponse response = timelapseService.incomingPromises(defaultRequest);

            Assertions.assertThat(response.getPromises()).isNotNull();
        }
    }

}