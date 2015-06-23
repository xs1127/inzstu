package cn.edu.zstu.tools;



/**
 * 暂时用不到
 * @author sjtu
 *
 */
public class FilterRequest {

	private int groupPos;
	private int childPos;
	private String requestString;
	private String url_path;
	public FilterRequest(int groupPos,int childPos,String requeString,String url_path) {
		// TODO Auto-generated constructor stub
		this.groupPos = groupPos;
		this.childPos = childPos;
		this.requestString = requeString;
		this.url_path = url_path;
	}
	
	public void filterRequest(){
		
	}
}
