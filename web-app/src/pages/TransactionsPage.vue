<template>
  <q-page class="q-pa-lg">
    <div class="row">
      <div class="col-md-3">
        <Btn
          :btnTexte="'Deposit or take founds'"
          :btnIcone="'add'"
          class="q-pb-lg"
          @click="openDepositeDropOffPopUp()"
        />
      </div>
      <div class="col-md-5"></div>
      <div class="col-md-4">
        <q-input
          dense
          outlined
          label="Find transactions, users"
          debounce="350"
          v-model="search"
          @input="searchTransactionPage"
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
            label="Transactions loading"
            label-class="text-yellow"
            label-style="font-size: 1.1em"
          />
          <q-table
            :columns="columns"
            :rows="transactionsPageList"
            row-key="transactionsId"
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
                  <span v-if="col.name == 'delete'">
                    <BtnIcon
                      :btn-icone="'delete'"
                      :btn-couleur="'red'"
                      :btn-size="'xs'"
                      :btn-tooltip="'Deleted'"
                      @click="
                        openDeleteTransactionsPopUp(props.row.transactionsId)
                      "
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

  <q-dialog v-model="depositeDropOffPopUp" persistent>
    <q-card class="text-center q-pa-lg deposite-money-card">
      <q-card-section>
        <span class="sous-titre"
          >Deposite or take money <q-icon name="payment"
        /></span>
      </q-card-section>
      <q-card-actions class="row justify-evenly q-pb-lg">
        <q-select
          v-model="userPick"
          :options="usersList"
          label="Users list"
          option-value="userId"
          option-label="userIdentity"
          use-input
          hide-selected
          fill-input
          input-debounce="0"
          @filter="filterFn"
          style="width: 300px"
        >
          <template v-slot:no-option>
            <q-item>
              <q-item-section class="text-grey"> No results </q-item-section>
            </q-item>
          </template>
        </q-select>
      </q-card-actions>
      <q-card-actions class="row justify-evenly q-pb-lg">
        <q-radio
          v-model="selection"
          val="deposite"
          label="Deposite some money"
        />
        <q-radio v-model="selection" val="take" label="Take some money" />
      </q-card-actions>
      <q-card-actions class="row justify-evenly q-pb-lg">
        <TheInput
          v-model="transaction.transactionsAmount"
          :input-label="inputLabel"
          :input-type="'number'"
        />
      </q-card-actions>
      <q-card-actions class="row q-pb-lg justify-center">
        <TheInput
          v-model="transaction.transactionsMessage"
          :input-label="'Message'"
          :input-type="'textarea'"
        />
      </q-card-actions>
      <q-card-actions class="row justify-evenly q-pb-lg">
        <Btn
          :btnTexte="'Cancel'"
          :btnIcone="'cancel'"
          :btn-couleur="'red'"
          @click="cancelDepositeDropOffPopUp()"
        />
        <Btn
          :btnTexte="'Deposite money'"
          :btnIcone="'remove'"
          :btn-couleur="'green'"
          @click="DepositeDropOffPopUp()"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>

  <q-dialog v-model="deleteTransactionsPopUp">
    <q-card class="text-center q-pa-lg custom-card">
      <q-card-section>
        <span class="accentue"
          >Are you sure to delete the current transaction : <br />
          {{ transaction.transactionsAmount }} €<q-icon name="savings"
        /></span>
      </q-card-section>
      <q-card-actions class="row justify-evenly q-pb-lg">
        <Btn
          :btnTexte="'Cancel'"
          :btnIcone="'cancel'"
          @click="cancelDeleteTransactionsPopUp()"
        />
        <Btn
          :btnTexte="'Delete'"
          :btnIcone="'delete'"
          :btn-couleur="'red'"
          @click="deleteTransaction()"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import { defineComponent } from "vue";
import { transactionController } from "../utils/controller/transactionController.js";
import { userController } from "../utils/controller/userController.js";
import Btn from "../components/TheButton.vue";
import BtnIcon from "src/components/TheButtonIcon.vue";
import { UserDto } from "../utils/dto/UserDto.js";
import { TransactionDto } from "../utils/dto/TransactionDto.js";
import TheInput from "src/components/TheInput.vue";
import notify from "../utils/notify";

export default defineComponent({
  name: "TransactionsPage",
  components: {
    Btn,
    BtnIcon,
    TheInput,
  },
  computed: {
    inputLabel() {
      return `Money to ${this.selection} *`;
    },
  },
  data() {
    return {
      isHovered: false,
      depositeDropOffPopUp: false,
      visible: true,
      transactionsPageList: [],
      transaction: TransactionDto(),
      user: UserDto(),
      search: "",
      selection: "",
      userPick: null,
      pagination: {
        rowsPerPage: 8,
      },
      usersList: [],
      originalList: [],
      errors: null,
      deleteTransactionsPopUp: false,
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
          name: "transactionsAmount",
          required: true,
          label: "Transaction amount",
          field: "transactionsAmount",
          align: "center",
          sortable: true,
        },
        {
          name: "transactionsMessage",
          required: true,
          label: "Transaction message",
          field: "transactionsMessage",
          align: "center",
          sortable: true,
        },
        {
          name: "creationDate",
          required: true,
          label: "Creation date",
          field: "creationDate",
          align: "center",
          sortable: true,
        },
        {
          name: "delete",
          label: "Delete",
          align: "center",
        },
        {
          name: "account",
          label: "Account",
          align: "center",
        },
      ],
    };
  },

  async created() {
    await this.searchTransactionPage();
    await this.searchUsers();
  },
  watch: {
    search() {
      this.searchTransactionPage();
    },
  },

  methods: {
    async searchUsers() {
      try {
        const response = await userController.searchUsers("");
        this.usersList = response.data || [];
        this.originalList = response.data || [];
      } catch (error) {
        notify.error("Error fetching users:", error);
        this.usersList = [];
      } finally {
        this.visible = false;
      }
    },
    filterFn(val, update, abort) {
      update(() => {
        const needle = val.toLowerCase();
        this.usersList = this.originalList.filter((v) =>
          v.userIdentity.toLowerCase().includes(needle)
        );
      });
    },
    async searchTransactionPage() {
      try {
        const response = await transactionController.searchTransactionPage(
          this.search
        );
        this.transactionsPageList = response.data || [];
      } catch (error) {
        notify.error("Error fetching transactions:", error);
        this.transactionsPageList = [];
      } finally {
        this.visible = false;
      }
    },
    getTransactionById(transactionsId) {
      transactionController
        .getTransactionById(transactionsId)
        .then((response) => {
          if (response.data) {
            this.transaction = response.data;
          }
        })
        .catch((error) => {
          notify.error("Error when picking transaction's data");
        });
    },
    deleteTransaction() {
      transactionController
        .deleteTransaction(this.transaction.transactionsId)
        .then((response) => {
          if (response.status == 204) {
            notify.succes("transaction deleted succesfully");
            this.cancelDeleteTransactionsPopUp();
            this.searchTransactionPage();
          }
        })
        .catch((error) => {
          notify.error("Error during transaction's delete");
        });
    },
    openDeleteTransactionsPopUp(transactionsId) {
      this.deleteTransactionsPopUp = !this.deleteTransactionsPopUp;
      this.getTransactionById(transactionsId);
    },
    cancelDeleteTransactionsPopUp() {
      this.deleteTransactionsPopUp = !this.deleteTransactionsPopUp;
    },
    openAccountPage(userId) {
      this.$router.push(`account/${userId}`);
    },

    depositeMoney() {
      this.transaction.transactionsAmount = Math.abs(
        this.transaction.transactionsAmount
      );
    },
    dropOffMoney() {
      this.transaction.transactionsAmount = -Math.abs(
        this.transaction.transactionsAmount
      );
    },

    DepositeDropOffPopUp() {
      this.transaction.transactionsUserId = this.userPick.userId;
      if(this.selection === "deposite"){
        this.depositeMoney();
      }
      else{
        this.dropOffMoney();
      }
      transactionController
        .createTransaction(this.transaction)
        .then((response) => {
          if (response.data.statusList.length == 0) {
            notify.succes("Transaction created succesfully");
            this.transaction = TransactionDto();
            this.userPick = null;
            this.searchTransactionPage();
            this.depositeDropOffPopUp = !this.depositeDropOffPopUp;
          }
          else {
            response.data.statusList.forEach((status) => {
              notify.error("" + status.message);
            });
          }
        })
        .catch((error) => {
          notify.error("Error during the creation of the transaction");
        });
    },

    openDepositeDropOffPopUp() {
      this.transaction = TransactionDto();
      this.depositeDropOffPopUp = !this.depositeDropOffPopUp;
    },
    cancelDepositeDropOffPopUp() {
      this.userPick = null;
      this.transaction = TransactionDto();
      this.depositeDropOffPopUp = !this.depositeDropOffPopUp;
    },
  },
});
</script>
