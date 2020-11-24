package checkList;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

import lombok.Data;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Data
public class CheckList01 extends JFrame {

	private JLabel laName, lamsg;
	private JTextField tfName, tfmsg;
	private JButton submit, init;
	private Container c;
	private GridLayout grid;

	public CheckList01() {

		// 1. 필요한 오브젝트를 메모리에 로딩
		initObject();

		// 2. 세팅
		initSetting();

		// 3. 배치
		initBatch();

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				문자전송(tfName.getText(),tfmsg.getText());

			}
		});

		init.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tfName.setText("");
				tfmsg.setText("");

			}
		});

	}

	// 컴포넌트 혹은 컨테이너 초기화
	private void initObject() {
		laName = new JLabel("수신인");
		lamsg = new JLabel("내용");

		tfName = new JTextField("");
		tfmsg = new JTextField("");

		submit = new JButton("전송");
		init = new JButton("초기화");

		c = new Container();
		grid = new GridLayout();
	}

	private void initSetting() {
		setTitle("문자보내기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		c = getContentPane();
		GridLayout grid = new GridLayout(3, 2);
		grid.setVgap(5);
		c.setLayout(grid);
		setVisible(true);
	}

	private void initBatch() {
		c.add(laName);
		c.add(tfName);
		c.add(lamsg);
		c.add(tfmsg);
		c.add(submit);
		c.add(init);
	}

	static void 문자전송(String to, String text) {

		String api_key = "NCSNZPD8YK0SSP2Y";
		String api_secret = "XTGLEMVJAWLOOH5K1VO9HAA05RE27SLL";
		Message coolsms = new Message(api_key, api_secret);

		// 4 params(to, from, type, text) are mandatory. must be filled
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("to", to);
		params.put("from", "01048864197");
		params.put("type", "SMS");
		params.put("text", text);
		params.put("app_version", "test app 1.2"); // application name and version

		try {// 통신은 무조건 예외처리
			JSONObject obj = (JSONObject) coolsms.send(params);
			System.out.println("메기지가 전송되었습니다.");
			System.out.println(obj.toString());
			// 1번 obj(문자열) -> 자바 오브젝트
			// 2번 error가 있는지 확인
			// 3번 DB insert
		} catch (CoolsmsException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCode());
		}

	}

	public static void main(String[] args) {

		new CheckList01();
	}
}