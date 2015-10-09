package oneBuy.Util;

import oneBuy.Response.CapturaPedidoResponse;

public class InterfaceCapturarPedido {
	public OnTaskCompleted onTaskListenerComplete;
   
	public InterfaceCapturarPedido(OnTaskCompleted onTaskListenerComplete)
	{
		if(onTaskListenerComplete != null)
		{
			this.setOnTaskComplete(onTaskListenerComplete);
		}
	}
	
	   public interface OnTaskCompleted {
	        void onTaskCompleted(CapturaPedidoResponse objCapturaPedidoResponse);
	   }

	   private void setOnTaskComplete(OnTaskCompleted onTaskListenerComplete){
	        this.onTaskListenerComplete = onTaskListenerComplete;
	   }
}
