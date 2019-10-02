package ma.ensa.todo.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UserManager {
	private Connection con = null;
	private PreparedStatement pstm = null;
	private Statement stm = null;
	private ResultSet rs = null;

	public void ajouterUtilisateur() {
		System.out.println("=======>> L'ajout d'un utilisateur");
		con = DBConnection.getConnection();
		try {
			String sqlInsert = "insert into utilisateur(login,motpasse,email)value(?, ?,?)";
			pstm = con.prepareStatement(sqlInsert);
			// saisir les donn�es de l'utlisateur
			Scanner scanner = new Scanner(System.in);
			System.out.print("Veuillez saisir le Login :");
			String loginUser = scanner.nextLine();
			System.out.print("Veuillez saisir le mot de passe :");
			String pwUser = scanner.nextLine();
			System.out.print("Veuillez saisir l'email :");
			String emailUser = scanner.nextLine();
			pstm.setString(1, loginUser);
			pstm.setString(2, pwUser);
			pstm.setString(3, emailUser);
			int resultat = pstm.executeUpdate();
			if (resultat == 1) {
				System.out.println("Utilisateur bien enregistr�");
			} else {
				System.err.println("Probl�me d'enregistrement");
			}
		} catch (SQLException e) {
			DBConnection.getErrorException(e);
		} finally {
			DBConnection.close(con, null, pstm, rs);
		}
	}

	public int connecterUtilisateur() {
		System.out.println("=======>> Connexion d'un utilisateur");
		con = DBConnection.getConnection();
		int id = -1;
		try {
			String sqlConnect = "select * from utilisateur where login=? and motpasse=?";
			pstm = con.prepareStatement(sqlConnect);
			// saisir les donn�es de connexion
			Scanner scanner = new Scanner(System.in);
			System.out.print("Veuillez saisir le Login        :");
			String loginUser = scanner.nextLine();
			System.out.print("Veuillez saisir le mot de passe :");
			String pwUser = scanner.nextLine();
			pstm.setString(1, loginUser);
			pstm.setString(2, pwUser);

			rs = pstm.executeQuery();
			rs.first();
			if (rs.getRow() == 1) {
				System.out
						.println("Connexion r�ussie de : " + rs.getString("login") + "(" + rs.getString("email") + ")");
				id = rs.getInt("id");
			} else {
				System.err.println("Login ou mot de passe incorrecte");

			}
		} catch (SQLException e) {
			DBConnection.getErrorException(e);
		} finally {
			DBConnection.close(con, null, pstm, rs);
		}
		return id;
	}

	public void afficherTaches(int id) {
		System.out.println("===========>> L'affichage des taches <<========");
		con = DBConnection.getConnection();
		try {
			String sqlSelectTaches = "select * from tache where id=" + id;
			stm = con.createStatement();
			// saisir les donn�es de connexion
			rs = stm.executeQuery(sqlSelectTaches);
			while (rs.next()) {
				System.out.println("Tache N�" + rs.getRow() + " :[ " + rs.getInt("code") + ", "
						+ rs.getString("libelle") + ", " + rs.getInt("volume") + " h, " + rs.getDate("datefin") + "]");
			}
		} catch (SQLException e) {
			DBConnection.getErrorException(e);
		} finally {
			DBConnection.close(con, stm, null, rs);
		}

	}

	public void ajouterTache(int id) {
		System.out.println("====>> L'ajout d'une tache");
		con = DBConnection.getConnection();
		try {
			String sqlInsertTache = "insert into tache(libelle,volume,datefin,id)value(?,?,?,?)";
			pstm = con.prepareStatement(sqlInsertTache);
			// saisir les donn�es de l'utlisateur
			Scanner scanner = new Scanner(System.in);
			System.out.print("Libell� de la t�che             :");
			String libelleTache = scanner.nextLine();
			System.out.print("Volume horaire de la tache      :");
			int volumeTache = Integer.parseInt(scanner.nextLine());
			System.out.print("Date fin de la tache(yyyy-mm-dd):");
			Date datefinTache = Date.valueOf(scanner.nextLine());
			pstm.setString(1, libelleTache);
			pstm.setInt(2, volumeTache);
			pstm.setDate(3, datefinTache);
			pstm.setInt(4, id);
			int resultat = pstm.executeUpdate();
			if (resultat == 1) {
				System.out.println("T�che ajout�e");
			} else {
				System.err.println("Probl�me d'ajout de la tache t�che");
			}
		} catch (SQLException e) {
			DBConnection.getErrorException(e);
		} finally {
			DBConnection.close(con, null, pstm, rs);
		}

	}

	public void supprimerTache(int id) {
		System.out.println("====>> La suppression d'une t�che");
		con = DBConnection.getConnection();
		try {
			String sqlDeleteTache = "delete from tache where code=?";
			pstm = con.prepareStatement(sqlDeleteTache);
			// Saisir les donn�es de l'utlisateur
			Scanner scanner = new Scanner(System.in);
			System.out.print("Code de la t�che � supprimer:");
			int codeTache = Integer.parseInt(scanner.nextLine());
			pstm.setInt(1, codeTache);
			int resultat = pstm.executeUpdate();
			if (resultat == 1) {
				System.out.println("T�che supprim�e");
			} else {
				System.err.println("Probl�me de suppression de la t�che");
			}
		} catch (SQLException e) {
			DBConnection.getErrorException(e);
		} finally {
			DBConnection.close(con, null, pstm, rs);
		}

	}

	public void modifierTache(int id) {
		System.out.println("====>> La modification d'une tache");
		con = DBConnection.getConnection();
		try {
			String sqlUpdatetTache = "Update tache set libelle=?, volume=?, datefin=? where code=?";
			pstm = con.prepareStatement(sqlUpdatetTache);
			// saisir les donn�es de l'utlisateur
			Scanner scanner = new Scanner(System.in);
			System.out.print("Code de la t�che � modifier:");
			int codeTache = Integer.parseInt(scanner.nextLine());
			System.out.print("Nouveau Libell�              :");
			String libelleTache = scanner.nextLine();
			System.out.print("Nouveau Volume horaire       :");
			int volumeTache = Integer.parseInt(scanner.nextLine());
			System.out.print("Nouvelle Date fin(yyyy-mm-dd):");
			Date datefinTache = Date.valueOf(scanner.nextLine());
			pstm.setString(1, libelleTache);
			pstm.setInt(2, volumeTache);
			pstm.setDate(3, datefinTache);
			pstm.setInt(4, codeTache);
			int resultat = pstm.executeUpdate();
			if (resultat == 1) {
				System.out.println("T�che modifi�e");
			} else {
				System.err.println("Probl�me de modification de la tache");
			}
		} catch (SQLException e) {
			DBConnection.getErrorException(e);
		} finally {
			DBConnection.close(con, null, pstm, rs);
		}

	}

}
