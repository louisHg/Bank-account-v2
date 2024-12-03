import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatHeaderRowDef, MatRowDef, MatColumnDef } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { UserDto } from '../../models/user.dto';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-users-page',
  standalone: true,
  imports: [
    MatTableModule,
    MatPaginatorModule,
    MatSortModule
  ],
  templateUrl: './users-page.component.html',
  styleUrls: ['./users-page.component.scss'],
})
export class UsersPageComponent implements OnInit {
  public dataSource = new MatTableDataSource<UserDto>();
  
  private apiUrl = 'http://localhost:8080/';
  
  public displayedColumns: string[] = [
    'identity',
    'account_balance',
    'email',
    'phone_number',
    'address',
    'job_title',
  ];

  constructor(private api: UserService) {}

  public ngOnInit(): void {
    // this.api.searchUsers('').subscribe((users) => {
    //     this.dataSource.data = users;
    // });
    this.api.searchUsers('').subscribe({
      next: (response) => {
        console.log('Search results:', response);
        this.dataSource.data = response;
      },
      error: (err) => {
        console.error('Error:', err.message);
      }
    });
    
  }
}
