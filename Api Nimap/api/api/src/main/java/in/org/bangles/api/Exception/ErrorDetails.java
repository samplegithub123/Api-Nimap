package in.org.bangles.api.Exception;

public class ErrorDetails {
	private String message;
	private boolean error;

	public ErrorDetails(String message, boolean error) {
		super();
		this.message = message;
		this.error = error;
	}

	public final String getMessage() {
		return message;
	}

	public final void setMessage(String message) {
		this.message = message;
	}

	public final boolean isError() {
		return error;
	}

	public final void setError(boolean error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "ErrorDetails [message=" + message + ", error=" + error + "]";
	}

}
