package oneBuy.Util;

import oneBuy.Response.SondaPedidoResponse;

public class InterfaceSondaPedido {
	public OnTaskCompleted onTaskListenerComplete;

	public InterfaceSondaPedido(OnTaskCompleted onTaskListenerComplete)
	{
		if(onTaskListenerComplete != null)
		{
			this.setOnTaskComplete(onTaskListenerComplete);
		}
	}
	
	   public interface OnTaskCompleted {
	        void onTaskCompleted(SondaPedidoResponse objSondaPedidoResponse);
	   }

	   private void setOnTaskComplete(OnTaskCompleted onTaskListenerComplete){
	        this.onTaskListenerComplete = onTaskListenerComplete;
	   }
}
