package com.example.bank_account_project.utils.validable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StatusHelper {

    public void addStatus( final List< Status > existingStatus , final Status s ) {
        if ( existingStatus == null ) {
            throw new IllegalArgumentException( "existingStatus null" );
        }
        existingStatus.add( s );
    }

    public void addStatusList( final List< Status > existingStatus , final List< Status > newStatus ) {
        if ( existingStatus == null ) {
            throw new IllegalArgumentException( "existingStatus null" );
        }
        existingStatus.addAll( newStatus );
    }

    public void addStatusList( final Validable< ? > v , final List< Status > newStatus ) {
        if ( v.getStatusList() == null ) {
            v.setStatusList( new ArrayList<>() );
        }
        this.addStatusList( v.getStatusList(), newStatus );
    }

    public void addStatus( final Validable< ? > v , final Status newStatus ) {
        if ( v.getStatusList() == null ) {
            v.setStatusList( new ArrayList<>() );
        }
        this.addStatus( v.getStatusList(), newStatus );
    }

    public < T extends Object > void setObjectAndAddStatus( final Validable< T > target , final Validable< T > source ) {
        this.addStatusList( target, source.getStatusList() );
        target.setObject( source.getObject() );
    }

    public void addStatusList( final List< Status > existingStatus , final String messageCode , final StatusType type) {
        this.addStatus( existingStatus, new Status( messageCode, type) );
    }

    public boolean isInError( final List< Status > l ) {
        return ( l != null ) && l.stream().anyMatch( s -> StatusType.ERREUR_PARAMETRAGE.equals( s.getType() ) || StatusType.TECH_ERROR.equals( s.getType() )
                || StatusType.APP_ERROR.equals( s.getType() ) || StatusType.SECURITY.equals( s.getType() ) );
    }
    public boolean isNotInError(final List<Status> l){
        return !isInError(l);
    }

    public boolean isInErrorOrWarning( final List< Status > l ) {
        return ( l != null ) && l.stream().anyMatch( s -> StatusType.ERREUR_PARAMETRAGE.equals( s.getType() ) || StatusType.TECH_ERROR.equals( s.getType() ) || StatusType.WARNING.equals( s.getType() )
                || StatusType.APP_ERROR.equals( s.getType() ) || StatusType.SECURITY.equals( s.getType() ) );
    }

    public boolean addStatusListThenCheckIfinError( final List< Status > existingStatus , final List< Status > addedStatus ) {
        this.addStatusList( existingStatus, addedStatus );
        return ( existingStatus != null ) && existingStatus.stream().anyMatch( s -> StatusType.ERREUR_PARAMETRAGE.equals( s.getType() ) || StatusType.TECH_ERROR.equals( s.getType() )
                || StatusType.APP_ERROR.equals( s.getType() ) || StatusType.SECURITY.equals( s.getType() ) );
    }

    public boolean addStatusListThenCheckIfinError( final Validable< ? > v , final List< Status > addedStatus ) {
        this.addStatusList( v, addedStatus );
        return ( v.getStatusList() != null ) && v.getStatusList().stream().anyMatch( s -> StatusType.ERREUR_PARAMETRAGE.equals( s.getType() ) || StatusType.TECH_ERROR.equals( s.getType() )
                || StatusType.APP_ERROR.equals( s.getType() ) || StatusType.SECURITY.equals( s.getType() ) );
    }

    public boolean isInError( final Validable< ? > v ) {
        return ( v.getStatusList() != null ) && v.getStatusList().stream().anyMatch( s -> StatusType.ERREUR_PARAMETRAGE.equals( s.getType() ) || StatusType.TECH_ERROR.equals( s.getType() )
                || StatusType.APP_ERROR.equals( s.getType() ) || StatusType.SECURITY.equals( s.getType() ) );
    }

    public List< Status > initialize() {
        return new ArrayList< Status >();
    }

    public void clean( final List< Status > statusList ) {
        if ( statusList != null ) {
            statusList.clear();
        }
    }

    public String toString( final List< Status > list ) {
        String ret = "";
        if ( list != null ) {
            for ( final Status s : list ) {
                ret += s.toString() + "\n\r";
            }
        }
        return StringUtils.substringBeforeLast( ret, "\n\r" );
    }

    public String getMessagesList( final List< Status > list ) {
        String ret = "";
        if ( list != null ) {
            for ( final Status s : list ) {
                ret += s.getMessage() + "\n\r";
            }
        }
        return StringUtils.substringBeforeLast( ret, "\n\r" );
    }
}
