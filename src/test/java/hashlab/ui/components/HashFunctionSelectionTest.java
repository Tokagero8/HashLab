package hashlab.ui.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HashFunctionSelectionTest {

    @BeforeAll
    public static void setUpClass() {
        Platform.startup(() -> {});
    }

    @Test
    void testAlgorithmSelection() {
        UIComponentFactory factory = mock(UIComponentFactory.class);
        ComboBox<String> mockComboBox = new ComboBox<>();
        when(factory.getAlgorithmChoice()).thenReturn(mockComboBox);

        mockComboBox.getItems().addAll("SHA-256", "MD5");
        factory.getAlgorithmChoice().getSelectionModel().select("SHA-256");

        assertEquals("SHA-256", mockComboBox.getSelectionModel().getSelectedItem());
        verify(factory, times(1)).getAlgorithmChoice();
    }
}
