import java.sql.*;
public class report2 {

	public static void main(String[] args)
	{
		String usr ="postgres";
		String pwd ="";
		String url ="jdbc:postgresql://localhost:5432/postgres";

		try
		{
			Class.forName("org.postgresql.Driver");
			System.out.println("Success loading Driver!");
		}

		catch(Exception e)
		{
			System.out.println("Fail loading Driver!");
			e.printStackTrace();
		}

		try
		{
			Connection conn = DriverManager.getConnection(url, usr, pwd);
			System.out.println("Success connecting server!");

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Sales");
			
			String[] custs = new String[600];
			String[] prods = new String[600];
			int[] month = new int[600];
			
			int[] sum = new int[600];
			int[] count = new int[600];
			int[] before_sum = new int[600];
			int[] before_count = new int[600];
			int[] after_sum = new int[600];
			int[] after_count = new int[600];
			int[] max = new int[600];
			
			while (rs.next())
			{
				boolean IsBeforeNew = true;
				boolean IsAfterNew = true;
				boolean IsTheNew = true;
				
				int dbQuant = Integer.parseInt(rs.getString("quant"));
				int dbMonth = Integer.parseInt(rs.getString("month"));
				int i = 0;
				// figure out if the data is in the array or not
				for (i = 0; i < prods.length && prods[i] != null; i++){
					// data is in the array already
					if (rs.getString("cust").equals(custs[i]) && rs.getString("prod").equals(prods[i]) && dbMonth == month[i]-1)
					{
						IsBeforeNew = false;
						before_count[i]++;
						before_sum[i] += dbQuant;
					}
					if (rs.getString("cust").equals(custs[i]) && rs.getString("prod").equals(prods[i]) && dbMonth == month[i]+1)
					{
						IsAfterNew = false;
						after_count[i]++;
						after_sum[i] += dbQuant;
					}
					if(rs.getString("cust").equals(custs[i]) && rs.getString("prod").equals(prods[i]) && dbMonth == month[i])
					{
						IsTheNew = false;
						count[i]++;
						sum[i] += dbQuant;
						max[i] = max[i] < dbQuant ? dbQuant : max[i];
					}
				}
				//data is not in the array
				if (IsBeforeNew && dbMonth != 12)
				{
					custs[i] = rs.getString("cust");
					prods[i] = rs.getString("prod");
					month[i] = dbMonth+1;
				    before_sum[i] = dbQuant;
				    before_count[i]++;
				    i++;
				}
				if (IsAfterNew && dbMonth != 1)
				{
					custs[i] = rs.getString("cust");
					prods[i] = rs.getString("prod");
					month[i] = dbMonth-1;
				    after_sum[i] = dbQuant;
				    after_count[i]++;
				    i++;
				}
				if(IsTheNew)
				{
					custs[i] = rs.getString("cust");
					prods[i] = rs.getString("prod");
					month[i] = dbMonth;
					sum[i] = dbQuant;
				    count[i]++;
				    max[i] = dbQuant;
				}
				
			}
			

			ResultSet rs2 = stmt.executeQuery("SELECT * FROM Sales");

			String[] r3_prods = new String[600];
			String[] r3_custs = new String[600];
			int r3_month[] = new int[600];
			int r3_after_count[] = new int[600];
			int r3_before_count[] = new int[600];
			while (rs2.next())
			{
				boolean isBefore = false;
				boolean isAfter = false;
				String dbProd = rs2.getString("prod");
				String dbCust = rs2.getString("cust");
				int dbQuant = Integer.parseInt(rs2.getString("quant"));
				int dbMonth = Integer.parseInt(rs2.getString("month"));
				
				//to see if the current row from db is a valid before/after count for a given month
				for(int i=0; i<600 && prods[i] != null; i++){
					if(prods[i].equals(dbProd) && custs[i].equals(dbCust) && month[i] == dbMonth+1 && count[i] != 0 && sum[i]/count[i] <= dbQuant && max[i] >= dbQuant){
						isBefore = true;
					}
					
					if(prods[i].equals(dbProd) && custs[i].equals(dbCust) && month[i] == dbMonth-1 && count[i] != 0 && sum[i]/count[i] <= dbQuant && max[i] >= dbQuant){
						isAfter = true;
					}
				}
				
				//if either before or after count is valid
				if(isBefore || isAfter){
					int i=0;
					for(; i<600 && r3_prods[i] != null; i++){
						if(isBefore && r3_prods[i].equals(dbProd) && r3_custs[i].equals(dbCust) && r3_month[i] == dbMonth+1){
							r3_before_count[i] ++;
							isBefore = false;
						}
						
						if(isAfter && r3_prods[i].equals(dbProd) && r3_custs[i].equals(dbCust) && r3_month[i] == dbMonth-1){
							r3_after_count[i] ++;
							isAfter = false;
						}
					}
					
					if(isBefore){
						r3_prods[i] = dbProd;
						r3_custs[i] = dbCust;
						r3_month[i] = dbMonth + 1;
						r3_before_count[i] ++;
						i++;
					}
					
					if(isAfter){
						r3_prods[i] = dbProd;
						r3_custs[i] = dbCust;
						r3_month[i] = dbMonth - 1;
						r3_after_count[i] ++;
					}
				}
			}
			
			for(int i = 0; i < 600 && prods[i] != null; i++)
			{
				if (after_count[i] != 0 && before_count[i] != 0)
				{
					System.out.println(custs[i] + " " + prods[i] + " " + month[i] + " " + before_sum[i]/before_count[i] + " " + after_sum[i]/after_count[i]);
				}else if (after_count[i] == 0 && before_count[i] != 0){
					System.out.println(custs[i] + " " + prods[i] + " " + month[i] + " " + before_sum[i]/before_count[i] + " " + "0");
				}else if (after_count[i] != 0 && before_count[i] == 0){
					System.out.println(custs[i] + " " + prods[i] + " " + month[i] + " " + "0" + " " + after_sum[i]/after_count[i]);
				}
			}
			
			System.out.println("\n\n\nReport 3\n");
			for(int i = 0; i < 600 && r3_prods[i] != null; i++)
			{
				if (r3_after_count[i] != 0 || r3_before_count[i] != 0)
				{
					System.out.println(r3_custs[i] + " " + r3_prods[i] + " " + r3_month[i] + " " + r3_before_count[i] + " " + r3_after_count[i]);
				}
			}
		}
	

		catch(SQLException e)
		{
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}

	}

}