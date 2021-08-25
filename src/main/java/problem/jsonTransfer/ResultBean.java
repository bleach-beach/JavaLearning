package problem.jsonTransfer;

import java.io.Serializable;

/**
 * @ClassName: ResultBean
 * @Description: 请求结果封装
 * 
 */
public class ResultBean implements Serializable {

	private static final long serialVersionUID = 505021546957261239L;
	/**
	 * 是否成功标志
	 */
	private boolean success;
	/**
	 * 结果码 
	 */
	private String code;
	/**
	 * 结果信息
	 */
	private String message;
	/**
	 * 数据
	 */
	private Object data;

	public ResultBean() {
		super();
	}

	public ResultBean(boolean success, String code, String message, Object data) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	

	public ResultBean(boolean success, String code, String message) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


	@Override
	public String toString() {
		return "ResultBean{" +
				"success=" + success +
				", code='" + code + '\'' +
				", message='" + message + '\'' +
				", data=" + data +
				'}';
	}
}
