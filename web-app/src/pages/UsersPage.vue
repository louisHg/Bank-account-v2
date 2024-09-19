<template>
  <q-page class="q-pa-lg">
    <div class="row">
      <div class="col-md-3">
        <Btn
          :btnTexte="'Add a user'"
          :btnIcone="'add'"
          class="q-pb-lg"
          @click="openUserPopUp()"
        />
      </div>
      <div class="col-md-5"></div>
      <div class="col-md-4">
        <q-input
          dense
          outlined
          label="Find users"
          debounce="350"
          v-model="search"
          @input="searchUserPage"
        >
          <template v-slot:prepend>
            <q-icon name="search" />
          </template>
        </q-input>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <q-card class="medium-card">
          <q-inner-loading
            :showing="visible"
            color="yellow"
            label="Users loading"
            label-class="text-yellow"
            label-style="font-size: 1.1em"
          />
          <q-table
            :columns="columns"
            :rows="usersList"
            row-key="userName"
            :pagination="pagination"
            :rows-per-page-options="[0]"
          >
            <template #body="props">
              <q-tr
                :props="props"
                :class="{ 'hovered-row': isHovered }"
                @mouseover="isHovered = true"
                @mouseout="isHovered = false"
              >
                <q-td v-for="col in props.cols" :key="col.name" :props="props">
                  <span v-if="col.name == 'modify'">
                    <BtnIcon
                      :btn-icone="'edit'"
                      :btn-couleur="'dark'"
                      :btn-size="'xs'"
                      :btn-tooltip="'Modified'"
                      @click="openUserPopUp(props.row.userId)"
                    />
                  </span>
                  <span v-if="col.name == 'delete'">
                    <BtnIcon
                      :btn-icone="'delete'"
                      :btn-couleur="'red'"
                      :btn-size="'xs'"
                      :btn-tooltip="'Deleted'"
                      @click="openDeleteUserPopUp(props.row.userId)"
                    />
                  </span>
                  <span v-if="col.name == 'account'">
                    <BtnIcon
                      :btn-icone="'balance'"
                      :btn-couleur="'green'"
                      :btn-size="'xs'"
                      :btn-tooltip="'Account'"
                      @click="openAccountPage(props.row.userId)"
                    />
                  </span>
                  <span v-else>
                    {{ col.value }}
                  </span>
                </q-td>
              </q-tr>
            </template>
          </q-table>
        </q-card>
      </div>
    </div>
  </q-page>

  <q-dialog v-model="userPopUp" persistent>
    <q-card class="text-center q-pa-lg custom-card">
      <q-card-section>
        <span class="sous-titre" v-if="user.userId == null"
          >Add a user <q-icon name="person"
        /></span>
        <span class="sous-titre" v-else
          >Modify {{ user.userName }} {{ user.userFirstName }}'s informations
          <q-icon name="perso"
        /></span>
      </q-card-section>
      <q-card-section>
        <div class="q-gutter-md row justify-evenly">
          <div class="input-container">
            <q-input
              v-model="user.userName"
              :error="errors && errors.userName"
              label="Name *"
              filled
              style="width: 100%"
            />
          </div>
          <div class="input-container">
            <q-input
              v-model="user.userFirstName"
              :error="errors && errors.userFirstName"
              label="First name *"
              filled
              style="width: 100%"
            />
          </div>
          <div class="input-container">
            <q-input
              v-model="user.userEmail"
              :error="errors && errors.userEmail"
              label="Email *"
              type="email"
              filled
              style="width: 100%"
            />
          </div>
        </div>
      </q-card-section>
      <q-card-section>
        <div class="q-gutter-md row justify-evenly">
          <div class="input-container">
            <q-input
              v-model="user.userPhoneNumber"
              :error="errors && errors.userPhoneNumber"
              label="Phone number *"
              filled
              style="width: 100%"
            />
          </div>
          <div class="input-container">
            <q-input
              v-model="user.userAddress"
              :error="errors && errors.userAddress"
              label="Address *"
              filled
              style="width: 100%"
            />
          </div>
          <div class="input-container">
            <q-input
              v-model="user.userJobTitle"
              :error="errors && errors.userJobTitle"
              label="Job title *"
              filled
              style="width: 100%"
            />
          </div>
        </div>
      </q-card-section>
      <q-card-section>
        <div class="row justify-center">
          <q-input
            filled
            v-model="user.userDateOfBirth"
            label="Date"
            class="q-mt-md"
          >
            <template v-slot:prepend>
              <q-icon name="event" class="cursor-pointer">
                <q-popup-proxy
                  cover
                  transition-show="scale"
                  transition-hide="scale"
                >
                  <q-date v-model="user.userDateOfBirth" mask="YYYY-MM-DD">
                    <div class="row items-center justify-end">
                      <q-btn v-close-popup label="Close" color="primary" flat />
                    </div>
                  </q-date>
                </q-popup-proxy>
              </q-icon>
            </template>
          </q-input>
        </div>
      </q-card-section>
      <q-card-actions class="row justify-evenly q-pb-lg">
        <Btn
          :btnTexte="'Cancel'"
          :btnIcone="'cancel'"
          :btn-couleur="'red'"
          @click="cancelUserPopUp()"
        />
        <Btn
          v-if="user.userId != null"
          :btnTexte="'Update'"
          :btnIcone="'person'"
          :btn-couleur="'green'"
          @click="modifyUser()"
        />
        <Btn
          v-else
          :btnTexte="'Add'"
          :btnIcone="'add'"
          :btn-couleur="'green'"
          @click="addUser()"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>

  <q-dialog v-model="deleteUserPopUp">
    <q-card class="text-center q-pa-lg custom-card">
      <q-card-section>
        <span class="accentue"
          >Are you sure to delete the user : {{ user.userName }}
          {{ user.userFirstName }} <q-icon name="perso"
        /></span>
      </q-card-section>
      <q-card-actions class="row justify-evenly q-pb-lg">
        <Btn
          :btnTexte="'Cancel'"
          :btnIcone="'cancel'"
          @click="cancelDeleteUserPopUp()"
        />
        <Btn
          :btnTexte="'Delete'"
          :btnIcone="'delete'"
          :btn-couleur="'red'"
          @click="deleteUser()"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import { defineComponent } from "vue";
import { userController } from "../utils/controller/userController.js";
import Btn from "../components/TheButton.vue";
import BtnIcon from "src/components/TheButtonIcon.vue";
import { UserDto } from "../utils/dto/UserDto.js";

import notify from "../utils/notify";

export default defineComponent({
  name: "UsersPage",
  components: {
    Btn,
    BtnIcon,
  },
  data() {
    return {
      isHovered: false,
      visible: true,
      search: "",
      usersList: [],
      user: UserDto(),
      pagination: {
        rowsPerPage: 8,
      },
      errors: null,
      userPopUp: false,
      deleteUserPopUp: false,
      columns: [
        {
          name: "userIdentity",
          required: true,
          label: "Identity",
          field: "userIdentity",
          align: "center",
          sortable: true,
        },
        {
          name: "userAccountBalance",
          required: true,
          label: "Account Balance",
          field: "userAccountBalance",
          align: "center",
          sortable: true,
        },
        {
          name: "userEmail",
          required: true,
          label: "Email",
          field: "userEmail",
          align: "center",
          sortable: true,
        },
        {
          name: "userPhoneNumber",
          required: true,
          label: "Phone number",
          field: "userPhoneNumber",
          align: "center",
          sortable: true,
        },
        {
          name: "userAddress",
          required: true,
          label: "Address",
          field: "userAddress",
          align: "center",
          sortable: true,
        },
        {
          name: "userJobTitle",
          required: true,
          label: "Job title",
          field: "userJobTitle",
          align: "center",
          sortable: true,
        },
        {
          name: "account",
          label: "Account",
          align: "center",
        },
        {
          name: "modify",
          label: "Modify",
          align: "center",
        },
        {
          name: "delete",
          label: "Delete",
          align: "center",
        },
      ],
    };
  },

  async created() {
    await this.searchUserPage();
  },

  watch: {
    search() {
      this.searchUserPage();
    },
  },

  methods: {
    async searchUserPage() {
      try {
        const response = await userController.searchUsers(this.search);
        this.usersList = response.data || [];
      } catch (error) {
        notify.error("Error fetching users:", error);
        this.usersList = [];
      } finally {
        this.visible = false;
      }
    },
    openUserPopUp(userId) {
      if (userId != null) {
        this.getUserById(userId);
      }
      this.userPopUp = !this.userPopUp;
    },
    openDeleteUserPopUp(userId) {
      if (userId != null) {
        this.getUserById(userId);
      }
      this.deleteUserPopUp = !this.deleteUserPopUp;
    },
    cancelUserPopUp() {
      this.user = UserDto();
      this.userPopUp = !this.userPopUp;
    },
    getUserById(userId) {
      userController
        .getUserById(userId)
        .then((response) => {
          if (response.data) {
            this.user = response.data;
          }
        })
        .catch((error) => {
          notify.error("Error when picking user's data");
        });
    },
    addUser() {
      userController
        .createUser(this.user)
        .then((response) => {
          if (response.data.statusList.length == 0) {
            notify.succes("User created succesfully");
            this.user = UserDto();
            this.searchUserPage();
            this.userPopUp = !this.userPopUp;
          } else {
            response.data.statusList.forEach((status) => {
              notify.error("" + status.message);
            });
          }
        })
        .catch((error) => {
          notify.error("Error during the creation of user");
        });
    },
    modifyUser() {
      userController
        .updateUser(this.user.userId, this.user)
        .then((response) => {
          if (response.data.statusList.length == 0) {
            notify.succes("User modified succesfully");
            this.user = UserDto();
            this.searchUserPage();
            this.userPopUp = !this.userPopUp;
          } else {
            response.data.statusList.forEach((status) => {
              notify.error("" + status.message);
            });
          }
        })
        .catch((error) => {
          notify.error("Error during user's modification");
        });
    },
    deleteUser() {
      userController
        .deleteUser(this.user.userId)
        .then((response) => {
          if (response.status == 204) {
            notify.succes("User deleted succesfully");
            this.user = UserDto();
            this.searchUserPage();
            this.deleteUserPopUp = !this.deleteUserPopUp;
          }
        })
        .catch((error) => {
          notify.error("Error during user's delete");
        });
    },
    cancelDeleteUserPopUp() {
      this.user = UserDto();
      this.deleteUserPopUp = !this.deleteUserPopUp;
    },
    openAccountPage(userId) {
      this.$router.push(`account/${userId}`);
    },
  },
});
</script>
