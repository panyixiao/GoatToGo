package gtg_model_subsystem;

public class Admin {
		/** userName is the users name from the login page
		*/
		private String username;
		
		/**password is the user password given from login page
		*/
		private String password;
		
		public Admin(String username, String password){
			this.username = username;
			this.password = password;
		}
		
		/** return string of users name
		*/
		public String getUsername(){
			return this.username;
		}
		
			/** return string of password for user
		*/
		public String getPassword(){
			return this.password;
		}

}
