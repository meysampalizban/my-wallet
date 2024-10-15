import { Component, inject, OnInit } from "@angular/core";
import {
  FormBuilder,
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from "@angular/forms";
import { ServerService } from "../server.service";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { faAt } from "@fortawesome/free-solid-svg-icons";
import { faPhone } from "@fortawesome/free-solid-svg-icons";
import { CommonModule } from "@angular/common";
import { Register } from "../dto/register";
import { SweetAlert2LoaderService, SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import Swal from 'sweetalert2';
import { Router } from "@angular/router";
import { Title } from "@angular/platform-browser";

@Component({
  selector: "app-register-user",
  standalone: true,
  imports: [ReactiveFormsModule, FontAwesomeModule, CommonModule, FormsModule, SweetAlert2Module],
  providers: [SweetAlert2LoaderService],
  templateUrl: "./register-user.component.html",
  styleUrl: "./register-user.component.css",
})

export class RegisterUserComponent implements OnInit {
  faAtIcon = faAt;
  faPhoneIcon = faPhone;
  registerForm !: FormGroup;
  sexOption: boolean = false;
  yearCount: number[] = [];
  monthCount: string[] = [
    "فروردین",
    "اردیبهشت",
    "خرداد",
    "تیر",
    "مرداد",
    "شهریور",
    "مهر",
    "آبان",
    "آذر",
    "دی",
    "بهمن",
    "اسفند",
  ];
  dayCount: number[] = [];

  firstName = new FormControl("", [Validators.required, Validators.minLength(3)]);
  lastName = new FormControl("", [Validators.required]);
  email = new FormControl("", [Validators.required, Validators.email]);
  phoneNumber = new FormControl(null,[Validators.required, Validators.maxLength(11), Validators.pattern("[09][0-9]{10}")]);
  nationalCode = new FormControl(null,[Validators.required, Validators.maxLength(10), Validators.pattern("[0-9]{10}")]);
  year = new FormControl(null,[Validators.required]);
  month = new FormControl(null,[Validators.required]);
  day = new FormControl(null,[Validators.required]);
  sex = new FormControl("", [Validators.required]);
  militaryStatus = new FormControl("none");


  constructor(
    private form: FormBuilder,
    private service: ServerService,
    private router: Router,
    private title: Title
  ) {
    title.setTitle("ثبت نام");
    this.yearCount = Array.from({ length: 103 }, (_, index) => index + 1300);
    this.dayCount = Array.from({ length: 31 }, (_, index) => index + 1);
  }


  ngOnInit(): void {
    this.registerForm = this.form.group({
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      phoneNumber: this.phoneNumber,
      nationalCode: this.nationalCode,
      year: this.year,
      month: this.month,
      day: this.day,
      sex: this.sex,
      militaryStatus: this.militaryStatus
    })
  }

  submit(value: any) {

    if (this.registerForm.valid) {
      let year = value.get('year').value;
      let month = value.get('month').value;
      let day = value.get('day').value;
      let birthDate = year + '-' + month + '-' + day;

      let register: Register = {
        "firstName": value.get("firstName").value,
        "lastName": value.get("lastName").value,
        "email": value.get("email").value,
        "phoneNumber": value.get("phoneNumber").value,
        "nationalCode": value.get("nationalCode").value,
        "birthDate": birthDate,
        "sex": value.get("sex").value,
        "militaryStatus": value.get("militaryStatus").value
      };

      console.log(register);

      this.service.createUser(register).subscribe(res => {
        if (res.statusCode == 201) {
          Swal.fire({
            title: "موفق",
            text: `"${res.messages?.['success']}"`,
            icon: 'success',
            confirmButtonText: 'تمام'
          });
          localStorage.setItem("userId", res.messages?.['userId']);
          this.router.navigate(["/wallet"]);
        }
      });
    }
  }

  ChangeSex(event: any) {
    if (event.target.value == "man") {
      this.sexOption = true;
    }
    if (event.target.value == "woman") {
      this.sexOption = false;
    }
  }
}

