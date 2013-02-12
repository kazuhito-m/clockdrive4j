package tk.bnbm.clockdrive4j.view;

import static jfx.messagebox.MessageBox.OK;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * RoadDriveRecorderのテストクラス。
 * @author kazuhito_m
 */
public class RoadDriveRecorderTest {

    private RoadDriveRecorder sut;

    @Before
    public void setUp() throws Exception {
        // 画面が止まる「メッセージボックス」部分だけオーバーライドしたもの。
        sut = new RoadDriveRecorder() {
            @Override
            protected int confirmMessage(String msg) {
                assertThat(msg, is(not(nullValue())));
                assertThat(msg.length() , is(not(0)));
                return OK;
            }
        };
    }

    @Test
    public void レコーディングを要求すると内部状態が記録中になる() {
        // Act
        sut.record(null);
        // Assert
        assertThat(sut.isRecording(), is(true));
    }

}
