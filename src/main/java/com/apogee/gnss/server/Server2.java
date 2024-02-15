package com.apogee.gnss.server;

import java.sql.Connection;
import java.sql.DriverManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.lognet.springboot.grpc.GRpcService;

import com.apogee.gnss.example.FormRequest;
import com.apogee.gnss.example.FormResponse;
import com.apogee.gnss.example.FormServceGrpc.FormServceImplBase;
import com.apogee.gnss.form.FormModel;

import io.grpc.stub.StreamObserver;

@GRpcService
public class Server2 extends FormServceImplBase {
	

    /*
	public void serverSideStreaming(FormRequest request, StreamObserver<FormResponse> responseObserver) {

		for (int i = 0; i < 50; i++) {

			FormResponse formResponse = FormResponse.newBuilder()
					.setFormId(i)
					.setFormName("mohit")
					.setRemark("this is just for testing.")
					.build();
			responseObserver.onNext(formResponse);
		}

		responseObserver.onCompleted();

	}
	*/
    
    public void serverSideStreaming(FormRequest request, StreamObserver<FormResponse> responseObserver) {
    	try {
    		
    		FormModel formModel = new FormModel(); // Example initialization

            Connection conn = null;
    		 Class.forName("com.mysql.cj.jdbc.Driver");
             conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/ntrip_survey", "root", "root");          
             formModel.setDbConnection(conn);  
             String status = formModel.conformUserId(request.getUserId());
             JSONObject userStatus = new JSONObject(status);

             if (!userStatus.getString("message").equalsIgnoreCase("success")) { 
     		 FormResponse formResponse = FormResponse.newBuilder().setFormId(0).setFormName("msg").setRemark("user is not authorized.").build();
 			 responseObserver.onNext(formResponse); 
             } else {
                 JSONArray arr = formModel.allFormInJSON();
                 
                 for(int i=0;i<arr.length();i++) {
                   JSONObject obj = new JSONObject(arr.get(i).toString());                   
                   FormResponse formResponse = FormResponse.newBuilder()
       					.setFormId((int)obj.get("form_id"))
       					.setFormName((String)obj.get("form_name"))
       					.setRemark((String)obj.get("remark"))
       					.build();
       		     	responseObserver.onNext(formResponse);     	 
                 }
             }
           
		} catch (Exception e) {
             e.printStackTrace();			
		}
    	
		responseObserver.onCompleted();

	}

}
